package org.khl.chat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.khl.chat.common.Constant;
import org.khl.chat.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	private TokenService tokens;
	
	public MyAuthenticationSuccessHandler(TokenService tokens, String defaultSuccessUrl, boolean alwaysUse) {
		this.tokens = tokens;
		setDefaultTargetUrl(defaultSuccessUrl);
		setAlwaysUseDefaultTargetUrl(alwaysUse);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException { 
		authentication.getPrincipal();
		CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
		response.addCookie(new Cookie(Constant.JWT_TOKEN, tokens.asToken(details)));
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
