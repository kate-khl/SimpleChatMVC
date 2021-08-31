package org.khl.chat.dto;

import javax.validation.constraints.NotNull;

public class LoginRequestDto {

	@NotNull
	private String username;
	private String password;

	public LoginRequestDto() {
	}

	public LoginRequestDto(String email, String password) {
		super();
		this.username = email;
		this.password = password;
	}

	public String getEmail() {
		return username;
	}

	public void setEmail(String email) {
		this.username = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
