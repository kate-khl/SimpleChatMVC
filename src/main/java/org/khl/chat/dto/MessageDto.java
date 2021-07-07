package org.khl.chat.dto;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class MessageDto {
	
	private Long id;
	private String value;
	private UserDto author;
	private OffsetDateTime date;

	public MessageDto() {}
	
	public MessageDto(Long id, String value, UserDto author, OffsetDateTime date) {
		super();
		this.id = id;
		this.value = value;
		this.author = author;
		this.date = date;
	}

	
	public MessageDto(String value, ChatDto chatDto, UserDto authorDto) {
		this.value = value;
		this.author = authorDto;
		this.date = OffsetDateTime.now();
	}
	
	
}
