package org.khl.chat.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.khl.chat.common.Constant;
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

	private final TokenService tokenService;
	private final UserDetailsService userDetailsService;

	@Autowired
	public AuthFilter(TokenService tokenService, UserDetailsService userDetailsService) {
		super();
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getTokenFromCookies(request);

		if (token != null && tokenService.verificationToken(token)) {
			String userLogin = tokenService.getUserFromToken(token).getEmail();
			authentiticate(userLogin);
		}
		filterChain.doFilter(request, response);
	}

	private void authentiticate(String userLogin) {

		CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userLogin);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
				customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private String getTokenFromCookies(HttpServletRequest request) {
		String token = null;

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.JWT_TOKEN)) {
					token = cookie.getValue();
				}
			}
		}
		return token;
	}
}
