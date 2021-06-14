package org.khl.chat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.khl.chat.common.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserRequest {

	@NotBlank
	private String name;
	@Email
	private String email;
	@NotBlank
	private String password;

	private Role role;

	public RegistrationUserRequest() {
	}

	public RegistrationUserRequest(@NotBlank @NotNull String name, @Email @NotNull String email,
			@NotBlank @NotNull String password, Role role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

}
