package com.web.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.boot.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);	
	User findByEmail(String email);
}
