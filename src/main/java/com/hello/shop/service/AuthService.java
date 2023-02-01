package com.hello.shop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.shop.domain.User;
import com.hello.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public User signup(User user){
		String password = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");

		User userEntity = userRepository.save(user);
		return userEntity;
	}

}
