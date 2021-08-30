package org.khl.chat.controller;

import org.khl.chat.dto.SendMessageRequest;
import org.khl.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/chat/api/v1")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class MessageController {

	MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@DeleteMapping("/messages/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable(name = "id") Long id) {
		messageService.delete(id);
	}

	@PostMapping("/messages/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void edit(@PathVariable(name = "id") Long msgId, @RequestBody SendMessageRequest smReq) {
		messageService.edit(msgId, smReq.getValue());
	}

}
