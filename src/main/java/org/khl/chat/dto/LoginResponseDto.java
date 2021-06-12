package org.khl.chat.dto;

public class LoginResponseDto {
	
	private String token;
	private UserDto userDto;
	
	public LoginResponseDto(String token, UserDto userDto) {
		super();
		this.token = token;
		this.userDto = userDto;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto status) {
		this.userDto = status;
	}
}
