package com.hello.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hello.shop.domain.Cart;
import com.hello.shop.domain.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	Cart findByUserId(int id);
	Cart findCartById(int id);
	Cart findCartByUserId(int id);

}
