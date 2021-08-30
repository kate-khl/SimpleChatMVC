package org.khl.chat.dto;

import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CreateChatRequest {

	private Collection<Long> userIds;
	private String name;

}
