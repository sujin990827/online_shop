package com.hello.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hello.shop.domain.Item;
import com.hello.shop.domain.User;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	Item findItemById(int id);


}
