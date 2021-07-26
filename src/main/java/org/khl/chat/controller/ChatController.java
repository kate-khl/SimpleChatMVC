package org.khl.chat.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.service.ChatService;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "http://127.0.0.1:8888", allowedHeaders = "Authorization")
@Controller
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ChatController {

	private final ChatService chatService;
	private final UserService userService;
	
	@Autowired
	public ChatController(@Qualifier("db") ChatService chatService
			, @Qualifier("db") UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}
	
	@GetMapping("/chat/private/{id}")
	public String getChatrFrame(Model model, @PathVariable Long id) {
		ChatDto ch = chatService.createPrivateChatWithUserIfNotExist(id);
		model.addAttribute("chat", ch);
		model.addAttribute("companion", userService.findById(id));
		return "ChatFrame";
	} 
	
	@PostMapping("/chats")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChatDto create(@RequestBody @Valid CreateChatRequest createReqChat) {
		ChatDto chatDto = chatService.createChat(createReqChat);
		return chatDto;
	}
	
	@GetMapping("/user/{id}/chats")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Collection<ChatDto> getChats(@PathVariable(name = "id") Long userId) {
		return chatService.getChats(userId);
	}
	
	@PostMapping("/chats/{id}/users")
	@ResponseStatus(code = HttpStatus.OK)
	public void addUsers(@RequestBody @Valid Collection <Long> userIds, @PathVariable(name = "id") Long chatId) {
		chatService.addUsers(userIds, chatId);
	}
   
	@DeleteMapping("/chats/{id}/users")
	@ResponseStatus(code = HttpStatus.OK)
	public void removeUsers(@RequestBody @Valid Collection <Long> userIds, @PathVariable(name = "id") Long chat_id) {
		chatService.removeUsers(userIds, chat_id);
	}
	
	@GetMapping("/chat")
	public String getAllUsers(Model model) {
		ArrayList<UserDto> users = (ArrayList<UserDto>)userService.getAllUsers();
		model.addAttribute("users", users);
		return "Main";
	}
	
}
