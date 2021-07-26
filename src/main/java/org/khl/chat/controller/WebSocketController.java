package org.khl.chat.controller;

import java.security.Principal;
import java.util.Collection;

import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.PageParams;
import org.khl.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@Autowired
	MessageService messageService;
	
	@SubscribeMapping("/chat/{id}") 
	public Collection<MessageDto> greeting(@DestinationVariable Long id) throws Exception {
		Collection<MessageDto> messages = messageService.getMessages(id, new PageParams(0, Integer.MAX_VALUE));
		return messages;
	}


	@MessageMapping("/chatr")
	public void message(@Payload Message<String> msg, Principal user, @Header("simpSessionId") String sessionId)	throws Exception {
		System.out.println("here");
//		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(),	new SimpleDateFormat("HH:mm").format(new Date()));
//		msg.
//		simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/secured/user/queue/specific-user", out);
	}
}
