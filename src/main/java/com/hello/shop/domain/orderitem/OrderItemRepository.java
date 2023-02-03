package com.hello.shop.domain.orderitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findOrderItemsByUserId(int userId);
    List<OrderItem> findAll();
    OrderItem findOrderItemById(int orderItemId);
}
