package org.khl.chat.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

	private Long id;
	private String name;
	private UserDto author;
	@Default
	private List<MessageDto> messages = new ArrayList<>();

}
