package org.khl.chat.security;

import java.util.Collection;
import java.util.Collections;

import org.khl.chat.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private String usrename;
	private String password;
	private Collection<? extends GrantedAuthority> grantedAuthorities;

	public static CustomUserDetails fromUserDtoToCustomUserDetails(UserDto userDto) {
		CustomUserDetails c = new CustomUserDetails();
		c.usrename = userDto.getEmail();
		c.password = userDto.getPassword();
		c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(userDto.getRole()));
		return c;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return usrename;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
