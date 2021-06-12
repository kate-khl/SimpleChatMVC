package org.khl.chat.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.khl.chat.dto.UserDto;

@Entity 
public class User {
	@Id
    @SequenceGenerator(name = "myGen")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column (unique = true)
	private String email;
	private String password;
	private String role;
	
	@ManyToMany(mappedBy = "users")
	private Collection<Chat> chats;
	
	public User () {};
	
	public User(Long id, String name, String email, String password, String role) {
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Collection<Chat> getChats() {
		return chats;
	}
	
	public void setChats(Collection<Chat> chats) {
		this.chats = chats;
	}
}
