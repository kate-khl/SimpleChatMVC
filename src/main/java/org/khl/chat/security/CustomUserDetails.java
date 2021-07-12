package org.khl.chat.security;

import java.util.Collection;
import java.util.Collections;

import org.khl.chat.common.Role;
import org.khl.chat.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Builder
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private Long id;
	private String usrename;
	@Getter
	private String email;
	private String password;
	@Getter
	private Role role;

	public static CustomUserDetails from(UserDto user) {
		CustomUserDetails details = CustomUserDetails.builder()
		.password(user.getPassword())
		.usrename(user.getName())
		.email(user.getEmail())
		.id(user.getId())
		.role(user.getRole())
		.build();
		return details;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
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
