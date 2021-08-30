package org.khl.chat.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

	private String token;
	private UserDto userDto;

	public LoginResponseDto(String token, UserDto userDto) {
		super();
		this.token = token;
		this.userDto = userDto;
	}

}
