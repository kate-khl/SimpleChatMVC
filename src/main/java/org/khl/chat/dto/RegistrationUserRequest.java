package org.khl.chat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserRequest {

	@NotBlank
	@NotNull
	private String name;
	@Email
	@NotNull
	private String email;
	@NotBlank
	@NotNull
	private String password;
	@NotBlank
	@NotNull
	private String role;

	public RegistrationUserRequest() {
	}

	public RegistrationUserRequest(@NotBlank @NotNull String name, @Email @NotNull String email,
			@NotBlank @NotNull String password, @NotBlank @NotNull String role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

}
