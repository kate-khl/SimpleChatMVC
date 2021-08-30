package org.khl.chat.config;

import org.khl.chat.common.Constant;
import org.khl.chat.security.AuthFilter;
import org.khl.chat.security.JwtAuthentiticationProvider;
import org.khl.chat.security.MyAuthenticationSuccessHandler;
import org.khl.chat.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig { 

	@Configuration
	@Order(1) 
	public static class jwtAuthConfig extends WebSecurityConfigurerAdapter{
		
		@Autowired
		private TokenService tokens;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/chat/**").authorizeRequests().anyRequest().hasRole("USER")
			.and().headers().frameOptions().disable()
			.and().authenticationProvider(new JwtAuthentiticationProvider(tokens)).csrf().disable()
			.addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin();
		}
	}

	@Configuration
	public static class LoginPasswordAuthConfig extends WebSecurityConfigurerAdapter{

		@Autowired
		private TokenService tokens;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll().successHandler(new MyAuthenticationSuccessHandler(tokens, "/chat", true))
			.and().logout().deleteCookies(Constant.JWT_TOKEN)
			.and().headers().frameOptions().disable()
			.and().httpBasic().disable().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
		
	}

}