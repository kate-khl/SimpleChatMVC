package org.khl.chat.entity;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.khl.chat.DateHelper;
import org.khl.chat.dto.MessageDto;

import lombok.Data;

@Data
@Entity
public class Message {

	@Id
    @SequenceGenerator(name = "msgGen")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String value;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;
	
	private OffsetDateTime date = DateHelper.now();
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "chat_id")
	private Chat chat;
	
	public Message() {}
	
	public Message(Long id, String value, User author, OffsetDateTime date) {
		super();
		this.id = id;
		this.value = value;
		this.author = author;
		this.date = date;
	}
	
	public Message(MessageDto msgDto) {
		super();
		this.id = msgDto.getId();
		this.value = msgDto.getValue();
		this.date = msgDto.getDate();
	}
	
	public Message(String value, User author, Chat chat) {

		this.value = value;
		this.author = author;
		this.date = DateHelper.now();
		this.chat = chat;
	}
	
	
}
