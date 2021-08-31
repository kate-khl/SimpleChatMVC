package org.khl.chat.service;

import java.util.Collection;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;

public interface ChatService {

	public ChatDto createPublicChat(CreateChatRequest chat);

	public void addUsers(Collection<Long> userIds, Long id);

	public void removeUsers(Collection<Long> userIds, Long id);

	public Collection<ChatDto> getChats(Long userId);

	public void removeChat(Long id);

	public ChatDto findChat(Long id);

	public ChatDto createPrivateChatWithUserIfNotExist(Long id);

}
