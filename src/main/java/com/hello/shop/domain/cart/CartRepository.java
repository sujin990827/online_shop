package com.hello.shop.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(int id);
    Cart findCartById(int id);
    Cart findCartByUserId(int id);
}
