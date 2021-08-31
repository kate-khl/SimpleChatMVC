package org.khl.chat.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.khl.chat.dao.ChatDao;
import org.khl.chat.dao.MessageDao;
import org.khl.chat.dao.UserDao;
import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.CreateChatRequest;
import org.khl.chat.entity.Chat;
import org.khl.chat.entity.ChatType;
import org.khl.chat.entity.User;
import org.khl.chat.exception.AccessControlException;
import org.khl.chat.mapper.ChatMapper;
import org.khl.chat.security.CustomUserDetails;
import org.khl.chat.security.UserHelper;
import org.khl.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ChatServiceImpl implements ChatService {

	private final ChatDao chDao;
	private final UserDao uDao;
	private final MessageDao msgDao;
	private final ChatMapper chatMapper;

	@Autowired
	public ChatServiceImpl(ChatDao chDao, UserDao uDao, MessageDao msgDao, ChatMapper chatMapper) {
		super();
		this.chDao = chDao;
		this.uDao = uDao;
		this.msgDao = msgDao;
		this.chatMapper = chatMapper;
	}

	@Override
	@Transactional
	public ChatDto createPrivateChatWithUserIfNotExist(Long userId) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User author = uDao.findByEmail(userDetails.getEmail()).get();
		User companion = uDao.findById(userId).get();
		Long hash = userId + author.getId();

		Chat chat = chDao.findByHashAndType(hash, ChatType.PRIVATE);
		if (chat == null) {
			chat = Chat.builder().author(author).hash(hash)
					.name("Chat " + author.getName() + " and " + companion.getName()).build();
			chat.getUsers().add(author);
			chat.getUsers().add(companion);
			chDao.save(chat);
		}
		return chatMapper.toDto(chat);
	}

	@Override
	@Transactional
	public ChatDto createPublicChat(CreateChatRequest request) {

		User author = uDao.findById(UserHelper.currentUser().getId()).get();
		String chatName = request.getName();
		Collection<User> users = uDao.findAllById(request.getUserIds());

		Chat c = Chat.builder().name(chatName).author(author).users(users).build();
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
		if (c.getAuthor().getId().equals(UserHelper.currentUser().getId())) {
			chDao.delete(c);
		} else
			throw new AccessControlException();
	}

}
