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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@Id
	@SequenceGenerator(name = "msgGen")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String value;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;

	private OffsetDateTime date;

	@ManyToOne(optional = false)
	@JoinColumn(name = "chat_id")
	private Chat chat;

}
