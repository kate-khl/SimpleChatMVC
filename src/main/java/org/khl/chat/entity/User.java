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

}
