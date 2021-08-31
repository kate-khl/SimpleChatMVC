package org.khl.chat.service;

import java.util.Collection;

import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.PageParams;
import org.khl.chat.dto.SendMessageRequest;

public interface MessageService {

	public MessageDto send(SendMessageRequest smReq, Long chatId);

	public void delete(Long id);

	public MessageDto edit(Long id, String text);

	public Collection<MessageDto> getMessages(Long id, PageParams pp);
}
