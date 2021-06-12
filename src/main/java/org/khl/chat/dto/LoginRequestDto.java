package org.khl.chat.dto;

import javax.validation.constraints.NotNull;

public class LoginRequestDto {

	@NotNull
	private String email;
	private String password;

	public LoginRequestDto() {
	}

	public LoginRequestDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
