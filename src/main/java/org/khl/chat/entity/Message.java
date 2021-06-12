package org.khl.chat.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.khl.chat.dto.MessageDto;

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
	
	private Date date;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "chat_id")
	private Chat chat;
	
	public Message() {}
	
	public Message(Long id, String value, User author, Date date) {
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
		this.author = new User(msgDto.getAuthor());
		this.date = msgDto.getDate();
	}
	
	public Message(String value, User author, Chat chat) {

		this.value = value;
		this.author = author;
		this.date = new Date();
		this.chat = chat;
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
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
}
