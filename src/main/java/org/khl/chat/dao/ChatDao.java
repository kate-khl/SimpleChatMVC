package org.khl.chat.dao;

import java.util.List;

import org.khl.chat.entity.Chat;
import org.khl.chat.entity.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatDao  extends JpaRepository<Chat, Long> {

	List<Chat> findByUsersId(Long id);
	Chat findByHashAndType(Long hash, ChatType type);
}
