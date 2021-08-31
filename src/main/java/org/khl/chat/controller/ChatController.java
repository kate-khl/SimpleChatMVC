package org.khl.chat.controller;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;
import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.PageParams;
import org.khl.chat.dto.SendMessageRequest;
import org.khl.chat.service.ChatService;
import org.khl.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/chat/api/v1")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ChatController {

	private final ChatService chatService;
	private final MessageService messageService;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public ChatController(ChatService chatService, MessageService messageService,
			SimpMessagingTemplate simpMessagingTemplate) {
		this.chatService = chatService;
		this.messageService = messageService;
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@PostMapping("/chats")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChatDto create(@RequestBody @Valid CreateChatRequest createReqChat) {
		ChatDto chatDto = chatService.createPublicChat(createReqChat);
		return chatDto;
	}

	@GetMapping("/users/{id}/chats")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<ChatDto> getChats(@PathVariable(name = "id") Long userId) {
		return chatService.getChats(userId);
	}

	@GetMapping("/chats/{id}/messages")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<MessageDto> getMessages(@PathVariable(name = "id") Long chatId, @RequestParam int page,
			@RequestParam int size) {
		return messageService.getMessages(chatId, new PageParams(page, size));
	}

	@PostMapping("/chats/{id}/messages")
	@ResponseStatus(code = HttpStatus.OK)
	public void send(@PathVariable(name = "id") Long chatId, @RequestBody SendMessageRequest smReq) {
		MessageDto msg = messageService.send(smReq, chatId);
		simpMessagingTemplate.convertAndSend("/chat/" + chatId, Arrays.asList(msg));
	}

	@PostMapping("/chats/{id}/users")
	@ResponseStatus(code = HttpStatus.OK)
	public void addUsers(@RequestBody @Valid Collection<Long> userIds, @PathVariable(name = "id") Long chatId) {
		chatService.addUsers(userIds, chatId);
	}

	@DeleteMapping("/chats/{id}/users")
	@ResponseStatus(code = HttpStatus.OK)
	public void removeUsers(@RequestBody @Valid Collection<Long> userIds, @PathVariable(name = "id") Long chat_id) {
		chatService.removeUsers(userIds, chat_id);
	}

}
