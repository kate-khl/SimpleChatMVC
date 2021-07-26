package org.khl.chat.security;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserHelper {

	public static CustomUserDetails currentUser() {
		CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return details;
	} 
}
