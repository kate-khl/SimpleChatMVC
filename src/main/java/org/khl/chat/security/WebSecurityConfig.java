package org.khl.chat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthFilter authFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/js/**", "/css/**", "/img/**", "/webjars/**").permitAll()
				.antMatchers("/admin/*").hasRole("ADMIN")
				.antMatchers("/user/*").hasRole("USER")
				.antMatchers("/registation", "/login").permitAll()
//                .and().formLogin().loginPage("/login").permitAll()

				.and().addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
