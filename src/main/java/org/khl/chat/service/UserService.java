package org.khl.chat.service;

import java.util.Collection;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.RegistrationUserRequest;
import org.khl.chat.dto.UserDto;

public interface UserService {

	UserDto create(RegistrationUserRequest user);

	Collection<UserDto> getAllUsers();

	UserDto findById(Long id);

	boolean edit(UserDto userDto);

	boolean remove(Long id);

	boolean checkLogin(String email, String password);

	UserDto findUserByEmail(String Email);

	public Collection<ChatDto> getChats(Long userId);

	public Collection<UserDto> getUsers(Long chatId);

	public Collection<UserDto> findByName(String name);
}
