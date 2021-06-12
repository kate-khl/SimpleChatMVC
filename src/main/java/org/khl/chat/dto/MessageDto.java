package org.khl.chat.dto;

import java.util.Date;

import org.khl.chat.entity.Chat;
import org.khl.chat.entity.Message;
import org.khl.chat.entity.User;

public class MessageDto {
	
	private Long id;
	private String value;
	private UserDto author;
	private Date date;

	public MessageDto() {}
	
	public MessageDto(Long id, String value, UserDto author, Date date) {
		super();
		this.id = id;
		this.value = value;
		this.author = author;
		this.date = date;
	}

	
	public MessageDto(String value, ChatDto chatDto, UserDto authorDto) {
		this.value = value;
		this.author = authorDto;
		this.date = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UserDto getAuthor() {
		return author;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
