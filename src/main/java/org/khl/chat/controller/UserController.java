package org.khl.chat.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.LoginRequestDto;
import org.khl.chat.dto.LoginResponseDto;
import org.khl.chat.dto.RegistrationUserRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.service.ChatService;
import org.khl.chat.service.TokenService;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "http://127.0.0.1:8888")
@RestController
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class UserController {

	   private final UserService userService;
//	   private final ChatService chatService;
	   private final TokenService tokenService;

	   @Autowired
	   public UserController(@Qualifier("db") UserService userService, TokenService tokenService) {
	       this.userService = userService;
	       this.tokenService = tokenService;
	   }

	   
	   @DeleteMapping("/users/{id}")
	   @ResponseStatus(code = HttpStatus.OK)
	   public boolean remove(@PathVariable(name = "id") Long id) {
		   userService.remove(id);
		   return true;
	   }
	   
	   @GetMapping("/users/list")
	   @ResponseStatus(code = HttpStatus.OK)
	   public Collection<UserDto> readAll() {
		   return userService.getAllUsers();
	   }
	   
	   @GetMapping("/users/{id}")
	   @ResponseStatus(code = HttpStatus.OK)
	   public UserDto findById(@PathVariable(name = "id") Long id, @RequestHeader HttpHeaders headers) {		   
		   UserDto u = userService.findById(id);		   
		   return u;
	   }	   
	   @PatchMapping("/users/{id}")
	   @ResponseStatus(code = HttpStatus.OK)
	   public UserDto edit(@RequestBody @Valid UserDto userDto, @PathVariable(name = "id") Long userId) {
		   userService.edit(userDto);
		   return userService.findById(userDto.getId());
	   }
	   
		@GetMapping("/users/chats/{id}")
		@ResponseStatus(code = HttpStatus.OK)
		public Collection<UserDto> getUsers(@PathVariable(name = "id") Long chat_id) {
			return userService.getUsers(chat_id);
		}
		
		@GetMapping("/users")
		@ResponseStatus(code = HttpStatus.OK)
		public Collection<UserDto> findUser(@RequestParam String name) {
			return userService.findByName("%"+name+"%");
		}
}
