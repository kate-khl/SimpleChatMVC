package org.khl.chat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

public class MyAuthenticationFailureHandler extends ForwardAuthenticationFailureHandler{

	public MyAuthenticationFailureHandler(String forwardUrl) {
		super(forwardUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);
		
		System.out.println("0000000 - here");
	}
	
	

}