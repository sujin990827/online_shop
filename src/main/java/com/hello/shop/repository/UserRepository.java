package com.hello.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hello.shop.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	User findById(int id);
}
