package org.khl.chat.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.khl.chat.dto.UserDto;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/chat/api/v1")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@DeleteMapping("/users/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean removeUser(@PathVariable(name = "id") Long id) {
		userService.remove(id);
		return true;
	}

	@GetMapping("/users/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserDto getUserById(@PathVariable(name = "id") Long id, @RequestHeader HttpHeaders headers) {
		return userService.findById(id);
	}

	@PatchMapping("/users/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserDto editUser(@RequestBody @Valid UserDto userDto, @PathVariable(name = "id") Long userId) {
		userService.edit(userDto);
		return userService.findById(userDto.getId());
	}

	@GetMapping("/users/chats/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<UserDto> getUsersInChat(@PathVariable(name = "id") Long chat_id) {
		return userService.getUsers(chat_id);
	}

	@GetMapping("/users")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<UserDto> getUserByName(@RequestParam String name) {
		return userService.findByName(name);
	}

}
