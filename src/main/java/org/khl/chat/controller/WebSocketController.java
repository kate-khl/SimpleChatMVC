package org.khl.chat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public String greeting(String message) throws Exception {
		return message;
	}

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat")
	public void message(@Payload Message<String> msg, Principal user, @Header("simpSessionId") String sessionId)	throws Exception {
		System.out.println("here");
//		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(),	new SimpleDateFormat("HH:mm").format(new Date()));
//		msg.
//		simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/secured/user/queue/specific-user", out);
	}
}
