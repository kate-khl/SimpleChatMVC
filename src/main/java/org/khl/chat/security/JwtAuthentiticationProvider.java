package org.khl.chat.security;

import org.khl.chat.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthentiticationProvider implements AuthenticationProvider{

	private TokenService tokens;
	
	@Autowired
	public JwtAuthentiticationProvider(TokenService tokens) {
		this.tokens = tokens;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		String jwt = (String) token.getPrincipal();
		CustomUserDetails details = tokens.asDetails(jwt);
		if(details == null) {
			throw new BadCredentialsException("jwt: " + jwt);
		}
		return new JwtAuthenticationToken(details, null, details.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
