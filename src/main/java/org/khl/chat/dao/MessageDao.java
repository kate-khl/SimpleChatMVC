package org.khl.chat.dao;

import java.util.List;

import org.khl.chat.entity.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageDao extends PagingAndSortingRepository<Message, Long> {

	List<Message> findByChatId(Long chatId);
	// Page<Message> findByChatId(Long chatId, Pageable page);

}
