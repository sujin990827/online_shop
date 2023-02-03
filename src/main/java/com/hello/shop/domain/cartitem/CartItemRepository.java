package com.hello.shop.domain.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByCartIdAndItemId(int cartId, int itemId);
    CartItem findCartItemById(int id);
    List<CartItem> findCartItemByItemId(int id);
}
