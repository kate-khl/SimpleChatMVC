package org.khl.chat.controller;

import java.util.Arrays;
import java.util.Collection;

import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.PageParams;
import org.khl.chat.dto.SendMessageRequest;
import org.khl.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "http://127.0.0.1:8888")
@RestController
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class MessageController {

	MessageService messageService;

	@Autowired 
	public MessageController(@Qualifier("db") MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping ("/chat/api/v1/chats/{id}/messages")
	@ResponseStatus(code = HttpStatus.OK)
	public void send(@PathVariable(name = "id") Long chatId, @RequestBody SendMessageRequest smReq) {
		MessageDto msg = messageService.send(smReq, chatId);
		simpMessagingTemplate.convertAndSend("/chat/" + chatId, Arrays.asList(msg));
	}
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@DeleteMapping ("/messages/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete (@PathVariable(name = "id") Long id) {
		messageService.delete(id);
	}
	
	@PostMapping ("/messages/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void edit (@PathVariable(name = "id") Long msgId, @RequestBody SendMessageRequest smReq) {
		messageService.edit(msgId, smReq.getValue());
	}
	
	@GetMapping ("chats/{id}/messages")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<MessageDto> getMessages (@PathVariable(name = "id") Long chatId, @RequestParam int page, @RequestParam int size) {
		return messageService.getMessages(chatId, new PageParams(page, size));
	}
}
