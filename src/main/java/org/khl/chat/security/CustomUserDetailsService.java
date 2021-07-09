package org.khl.chat.security;

import org.khl.chat.dto.UserDto;
import org.khl.chat.service.TokenService;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto user = userService.findUserByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("not found " + username);
		}
		String token = tokenService.createToken(user);
		return CustomUserDetails.from(user, token);
	}
}
