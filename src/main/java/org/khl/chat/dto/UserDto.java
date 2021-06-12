package org.khl.chat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private Long id;
	@NotBlank
	@NotNull
	private String name;
	@Email
	@NotNull
	private String email;
	@NotBlank
	@NotNull
	private String role;
	@NotBlank
	@NotNull
	private String password;

	public UserDto() {
	}

	public UserDto(String name, String email, String password, String role) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
	}

}
