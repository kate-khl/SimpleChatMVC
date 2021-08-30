package org.khl.chat.service;

import org.khl.chat.dto.UserDto;
import org.khl.chat.security.CustomUserDetails;

public interface TokenService {

	public String createToken(UserDto dto);

	public boolean verificationToken(String token);

	public UserDto getUserFromToken(String token);

	public CustomUserDetails asDetails(String token);

	public String asToken(CustomUserDetails details);

	public boolean almostExpire(String token);

}
