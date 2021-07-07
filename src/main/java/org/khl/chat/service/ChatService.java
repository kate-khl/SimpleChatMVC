package org.khl.chat.service;

import java.util.Collection;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;
import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.Chat;

public interface ChatService {

	public ChatDto createChat(CreateChatRequest chat);
	public void addUsers(Collection<Long> userIds, Long id);
	public void removeUsers(Collection<Long> userIds, Long id);
	public Collection<ChatDto> getChats(Long userId);
	public void removeChat(Long id);
	public ChatDto findChat(Long id);
//	public Collection<MessageDto> getMessages(Long chatId);
	public ChatDto createPrivateChatWithUserIfNotExist(Long id);
	
	
	
}
