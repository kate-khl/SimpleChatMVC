package org.khl.chat.dto;

import java.util.Collection;

import org.springframework.stereotype.Component;

@Component
public class CreateChatRequest {

	private Collection<Long> userIds;
	private String name;
	private String message;

	public CreateChatRequest() {}
	
	public CreateChatRequest(Collection<Long> users, String name, String message) {
		this.userIds = users;
		this.name = name;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


	public Collection<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(Collection<Long> userIds) {
		this.userIds = userIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}
}
