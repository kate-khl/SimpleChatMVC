package org.khl.chat.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.khl.chat.exception.AccessControlException;
import org.khl.chat.service.TokenService;
import org.khl.chat.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION = "Authorization";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserDetailsService userDetailsService;

//	public AuthFilter(TokenService tokenService, UserDetailsService userDetailsService) {
//		super();
//		this.tokenService = tokenService;
//		this.userDetailsService = userDetailsService;
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String token = httpRequest.getHeader(AUTHORIZATION);
		if (token != null && tokenService.verificationToken(token)) {
			String userLogin = tokenService.getUserFromToken(token).getEmail();
			CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService
					.loadUserByUsername(userLogin);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
					customUserDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}
}
