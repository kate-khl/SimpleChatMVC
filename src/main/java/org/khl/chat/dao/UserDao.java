package org.khl.chat.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.khl.chat.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import antlr.collections.List;



public interface UserDao  extends JpaRepository<User, Long> {

	Optional<User>findByEmail(String email);
	
	ArrayList<User> findByNameLike(String name);
	
	ArrayList<User> findAll();
	
}
