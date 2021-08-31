package org.khl.chat.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.khl.chat.common.Constant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		findJwtAuthentitication(request);
		filterChain.doFilter(request, response);
	}

	private void findJwtAuthentitication(HttpServletRequest request) {
		String token = getTokenFromCookies(request);
		if (token != null) {
			SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(token));
		}
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
