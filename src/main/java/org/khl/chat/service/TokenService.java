package org.khl.chat.service;

import org.khl.chat.dto.UserDto;

public interface TokenService {

	public String getToken(String Email, String password);

	public boolean verificationToken(String token);

	public UserDto getUserFromToken(String token);

	public boolean almostExpire(String token);

}
