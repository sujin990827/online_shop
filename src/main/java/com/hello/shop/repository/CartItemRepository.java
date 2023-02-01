package com.hello.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hello.shop.domain.CartItem;
import com.hello.shop.domain.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	CartItem findByCartIdAndItemId(int cartId, int itemId);
	CartItem findCartItemById(int id);
	List<CartItem> findCartItemByItemId(int id);

}
