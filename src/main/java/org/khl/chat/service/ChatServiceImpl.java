package org.khl.chat.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.khl.chat.Session;
import org.khl.chat.dao.ChatDao;
import org.khl.chat.dao.MessageDao;
import org.khl.chat.dao.UserDao;
import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;
import org.khl.chat.entity.Chat;
import org.khl.chat.entity.Message;
import org.khl.chat.entity.User;
import org.khl.chat.exception.AccessControlException;
import org.khl.chat.mapper.ChatMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;


@Service
@Qualifier("db")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatDao chDao;
	@Autowired
	private UserDao uDao;
	@Autowired
	private MessageDao msgDao;
	@Autowired
	private Session session;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	@Transactional
	public ChatDto createChat(CreateChatRequest crChat) {
		
		Chat c = new Chat();
		c.setName(crChat.getName());
		c.setUsers(uDao.findAllById(crChat.getUserIds()));
		
		User author = uDao.findById(session.getId()).get();
		c.getUsers().add(author);
		c.setAuthor(author);
		chDao.save(c);
		
		msgDao.save(new Message(crChat.getMessage(), author, c));
		chDao.save(c);
		return chatMapper.toDto(c);
	}

	@Override
	@Transactional
	public ChatDto findChat(Long id) {
		Chat chat = chDao.getOne(id);
		return chatMapper.toDto(chat);
	}

	@Override
	public Collection<ChatDto> getChats(Long userId) {
		Collection<Chat> chats = chDao.findByUsersId(userId);
		
		return chatMapper.toListOfDto(chats);
	}

	@Override
	@Transactional
	public void addUsers(Collection<Long> userIds, Long chatId) {
		Collection<User> users = uDao.findAllById(userIds);
		Chat chat = chDao.getOne(chatId);
		chat.getUsers().addAll(users);
		chDao.save(chat);
	}

	@Override
	@Transactional
	public void removeUsers(Collection<Long> userIds, Long chatId) {
		
		Collection<User> users = uDao.findAllById(userIds);
		Chat chat = chDao.getOne(chatId);
		chat.getUsers().removeAll(users);
		chDao.save(chat);
	}

	@Override
	@Transactional
	public void removeChat(Long id) {
		Chat c = chDao.getOne(id);
		if(c.getAuthor().getId().equals(session.getId())) {
			chDao.delete(c);
		} else 
		throw new AccessControlException();
	}
	

}
