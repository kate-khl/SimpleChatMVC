package org.khl.chat.security;

import org.khl.chat.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthFilter authFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.headers().frameOptions().disable();
		http.
			httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
					.antMatchers("/js/**", "/css/**", "/img/**", "/webjars/**").permitAll()
					.antMatchers("/admin/*").hasRole("ADMIN")
					.antMatchers("/users/**").hasRole("USER")
					.antMatchers("/registation", "/login*").permitAll()
			.and()
				.headers().frameOptions().disable()
			.and()
				.formLogin()
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/users/list", true)
					.failureForwardUrl("/login?error=true")
			.and()
			.logout().logoutUrl("/logout").deleteCookies(Constant.JWT_TOKEN)
			.and()
			.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }

}
