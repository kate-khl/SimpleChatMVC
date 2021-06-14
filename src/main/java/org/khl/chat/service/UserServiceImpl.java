package org.khl.chat.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.khl.chat.dao.ChatDao;
import org.khl.chat.dao.UserDao;
import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.RegistrationUserRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.Chat;
import org.khl.chat.entity.User;
import org.khl.chat.exception.NotAuthorizeException;
import org.khl.chat.mapper.ChatMapper;
import org.khl.chat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("db")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao uDao;
	@Autowired
	private ChatDao chDao;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	@Transactional
	public UserDto create(RegistrationUserRequest user) {
		User u = userMapper.RegistrationUserRequestToEntity(user);
		uDao.save(u);
		return userMapper.toDto(u);
	}

	@Override
	@Transactional
	public Collection<UserDto> getAllUsers() {
		List<User> users = uDao.findAll();
		
		return userMapper.toListOfDto(users);
	}

	@Override
	@Transactional
	public UserDto findById(Long id) {
		User u = uDao.findById(id).get();
		return userMapper.toDto(u);
	}

	@Override
	@Transactional
	public boolean edit(UserDto uDto) {
		try {
			User user = userMapper.toEntity(uDto);
			uDao.save(user);
			return true;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}

	@Override
	@Transactional
	public boolean remove(Long id) {
		try {
			uDao.deleteById(id);;
			return true;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}

	@Override
	@Transactional
	public boolean checkLogin(String email, String password) {
		User u = new User(); 
		try {
		u = uDao.findByEmail(email).get();
			if (u.getEmail().equals(email)) {
				if(u.getPassword().equals(password)) {
					return true;
				}
				else return false;
			}
			else return false;
		}
		catch (NoSuchElementException ex) {
			ex.getMessage();
			throw new NotAuthorizeException("Ошибка авторизации");
		}
	}

	@Override
	@Transactional
	public UserDto findUserByEmail(String email) {
		
		User u = uDao.findByEmail(email).get();
		return userMapper.toDto(u);
	}

	@Override
	@Transactional
	public Collection<ChatDto> getChats(Long userId) {
		Collection<Chat> chats = chDao.findByUsersId(userId);
		
		return chatMapper.toListOfDto(chats);
	}
	
	@Override
	@Transactional
	public Collection<UserDto> getUsers(Long chatId) {
		Chat c = chDao.getOne(chatId);
		return userMapper.toListOfDto(c.getUsers());
	}

	@Override
	@Transactional
	public Collection<UserDto> findByName(String name) {
		Collection<User> users  = uDao.findByNameLike("%" + name + "%");
		return userMapper.toListOfDto(users);
	}
}
