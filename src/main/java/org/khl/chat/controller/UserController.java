package org.khl.chat.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.khl.chat.dto.UserDto;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "http://127.0.0.1:8888")
@Controller
@RequestMapping("/user")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(@Qualifier("db") UserService userService) {
		this.userService = userService;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean removeUser(@PathVariable(name = "id") Long id) {
		userService.remove(id);
		return true;
	}

	@GetMapping("/list")
	public ModelAndView  getAllUsers(Model model) {
		ArrayList<UserDto> users = (ArrayList<UserDto>)userService.getAllUsers();
		ModelAndView mav = new ModelAndView("usersList");
		mav.addObject("users", "ss");
		return mav;
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserDto getUserById(@PathVariable(name = "id") Long id, @RequestHeader HttpHeaders headers) {
		UserDto u = userService.findById(id);
		return u;
	}

	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserDto editUser(@RequestBody @Valid UserDto userDto, @PathVariable(name = "id") Long userId) {
		userService.edit(userDto);
		return userService.findById(userDto.getId());
	}

	@GetMapping("/chats/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<UserDto> getUsersInChat(@PathVariable(name = "id") Long chat_id) {
		return userService.getUsers(chat_id);
	}

	@GetMapping("/")
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<UserDto> getUserByName(@RequestParam String name) {
		return userService.findByName(name);
	}
}
