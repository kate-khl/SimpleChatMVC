package org.khl.chat.service;

import org.khl.chat.exception.AccessControlException;
import org.khl.chat.mapper.MessageMapper;
import org.khl.chat.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.khl.chat.Session;
import org.khl.chat.dao.ChatDao;
import org.khl.chat.dao.MessageDao;
import org.khl.chat.dao.UserDao;
import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.PageParams;
import org.khl.chat.dto.SendMessageRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.Chat;
import org.khl.chat.entity.Message;
import org.khl.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Qualifier("db")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageDao msgDao; 
	@Autowired
	private ChatDao chDao; 
	@Autowired
	private UserDao uDao; 
	@Autowired
	private Session session;
	@Autowired
	private  MessageMapper messageMapper;
	
	@Override
	public MessageDto send(SendMessageRequest smReq, Long chatId) {
		
		Chat ch = chDao.findById(chatId).get();
		User author = uDao.findById(session.getId()).get();
		Message msg = new Message(smReq.getValue(), author, ch);
		msgDao.save(msg);
		
		return messageMapper.toDto(msg);
	}

	@Override
	public void delete(Long id) {
		
		Message msg = msgDao.findById(id).get();
		if(msg.getAuthor().getId().equals(session.getId())) {
			msgDao.delete(msg);
		} else 
			throw new AccessControlException();
	}

	@Override
	public MessageDto edit(Long id, String text) {
		
		Message msg = msgDao.findById(id).get();
		if(msg.getAuthor().getId().equals(session.getId())) {
			msg.setValue(text);
			msgDao.save(msg);
			return messageMapper.toDto(msg);
		} else 
			throw new AccessControlException();
	}
	
	@Override
	public Collection<MessageDto> getMessages(Long chatId, PageParams pp){
			
		Pageable psgeParams = PageRequest.of(pp.getPage(), pp.getSize());
		
		Collection<Message> msgs =  msgDao.findByChatId(chatId); //msgDao.findByChatId(chatId, psgeParams);
		Collection<MessageDto> msgDtos = messageMapper.toListOfDto(msgs);
		return msgDtos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

}
