package org.khl.chat.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.khl.chat.common.Role;
import org.khl.chat.dto.UserDto;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@SequenceGenerator(name = "myGen")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Column(unique = true)
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

	@ManyToMany(mappedBy = "users")
	private Collection<Chat> chats;

	public User() {
	}

	public User(Long id, String name, String email, String password, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User(UserDto u) {
		this.id = u.getId();
		this.name = u.getName();
		this.email = u.getEmail();
		this.role = u.getRole();
	}

}
