package org.khl.chat;

import javax.servlet.http.HttpServletRequest;

import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class SimpleChatApplication implements WebSocketMessageBrokerConfigurer {

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "127.0.0.1");
	    System.setProperty("https.proxyHost", "127.0.0.1");
	    System.setProperty("http.proxyPort", "8888");
	    System.setProperty("https.proxyPort", "8888"); 
	    
		SpringApplication.run(SimpleChatApplication.class, args);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.enableSimpleBroker("/chat-broker");
		config.setApplicationDestinationPrefixes("/app");
//		config.setUserDestinationPrefix("/secured/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").withSockJS();
	}

//	@Bean
//	public FilterRegistrationBean<AuthFilter> loggingFilter() {
//		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
//
//		registrationBean.setFilter(new AuthFilter());
//		registrationBean.addUrlPatterns("/users/*", "/chats/*", "/messages/*", "/user/*");
//		registrationBean.setOrder(0);
//
//		return registrationBean;
//	}

	@Bean
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
	public Session userBean(@Autowired HttpServletRequest req) {
		String token = req.getHeader("Authorization");
		return Session.fromToken(token);

	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		TypeMap<User, UserDto> tm = mapper.createTypeMap(User.class, UserDto.class);
		tm.addMappings(skipModifiedFieldsMap);

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);

		return mapper;
	}

	private PropertyMap<User, UserDto> skipModifiedFieldsMap = new PropertyMap<User, UserDto>() {
		protected void configure() {
			// skip().setPassword(null);
		}
	};

}
