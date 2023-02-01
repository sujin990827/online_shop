package com.hello.shop.service;

import java.util.Objects;

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
	private final CartService cartService;

	@Transactional
	public User signup(User user){

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(user.getRole());

		User userEntity = userRepository.save(user);

		// if (Objects.equals(userEntity.getRole(), "ROLE_SELLER")) {
		// 	saleService.createSale(user);
		// } else if (Objects.equals(user.getRole(), "ROLE_USER")){
		// 	cartService.createCart(user);
		// 	orderService.createOrder(user);
		// }
		if (Objects.equals(user.getRole(), "ROLE_USER")){
			cartService.createCart(user);
		}

		return userEntity;
	}

}
