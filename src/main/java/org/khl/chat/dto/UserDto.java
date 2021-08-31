package org.khl.chat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.khl.chat.common.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private Long id;
	@NotBlank
	private String name;
	@Email @NotNull
	private String email;
	private Role role;
	@NotBlank
	@NotNull
	private String password;

	public UserDto() {
	}

	public UserDto(Long id, String name, String email, String password, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public UserDto(Long id, @NotBlank @NotNull String name, @Email @NotNull String email, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
	

}
