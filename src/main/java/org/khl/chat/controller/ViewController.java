package org.khl.chat.controller;

import java.util.ArrayList;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.UserDto;
import org.khl.chat.service.ChatService;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "http://127.0.0.1:8888")
@Controller
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ViewController {

	private final ChatService chatService;
	private final UserService userService;

	@Autowired
	public ViewController(ChatService chatService, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}

	@GetMapping("/chat/private/{id}")
	public String getChatrFrame(Model model, @PathVariable Long id) {
		ChatDto chat = chatService.createPrivateChatWithUserIfNotExist(id);
		model.addAttribute("chat", chat);
		model.addAttribute("companion", userService.findById(id));
		return "chatFrame";
	}

	@GetMapping("/chat")
	public String getAllUsers(Model model) {
		ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getAllUsers();
		model.addAttribute("users", users);
		return "main";
	}
}
