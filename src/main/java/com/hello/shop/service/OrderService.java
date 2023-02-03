package com.hello.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.shop.domain.cartitem.CartItem;
import com.hello.shop.domain.item.Item;
import com.hello.shop.domain.order.Order;
import com.hello.shop.domain.order.OrderRepository;
import com.hello.shop.domain.orderitem.OrderItem;
import com.hello.shop.domain.orderitem.OrderItemRepository;
import com.hello.shop.domain.saleitem.SaleItem;
import com.hello.shop.domain.saleitem.SaleItemRepository;
import com.hello.shop.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserPageService userPageService;
    private final SaleItemRepository saleItemRepository;
    private final ItemService itemService;

    // 회원가입 하면 회원 당 주문 하나 생성
    public void createOrder(User user){

        Order order = Order.createOrder(user);

        orderRepository.save(order);
    }

    // id에 해당하는 주문아이템 찾기
    public List<OrderItem> findUserOrderItems(int userId) {
        return orderItemRepository.findOrderItemsByUserId(userId);
    }

    // OrderItem 하나 찾기
    public OrderItem findOrderitem(int orderItemId) {return orderItemRepository.findOrderItemById(orderItemId);}

    // 장바구니상품주문
    @Transactional
    public OrderItem addCartOrder(int itemId, int userId, CartItem cartItem, SaleItem saleItem) {

        User user = userPageService.findUser(userId);

        OrderItem orderItem = OrderItem.createOrderItem(itemId, user, cartItem, saleItem);

        orderItemRepository.save(orderItem);

        return orderItem;
    }

    // 주문하면 Order 만들기
    @Transactional
    public void addOrder(User user, List<OrderItem> orderItemList) {

        Order userOrder = Order.createOrder(user, orderItemList);

        orderRepository.save(userOrder);
    }

    // 단일 상품 주문
    @Transactional
    public void addOneItemOrder(int userId, Item item, int count, SaleItem saleItem) {

        User user = userPageService.findUser(userId);

        Order userOrder = Order.createOrder(user);

        OrderItem orderItem = OrderItem.createOrderItem(item.getId(), user, item, count, userOrder, saleItem);

        orderItemRepository.save(orderItem);
        orderRepository.save(userOrder);
    }

    // 주문 취소 기능
    @Transactional
    public void orderCancel(User user, OrderItem cancelItem) {

        Item item = itemService.itemView(cancelItem.getItemId());

        // 판매자의 판매내역 totalCount 감소
        cancelItem.getSaleItem().getSale().setTotalCount(cancelItem.getSaleItem().getSale().getTotalCount()-cancelItem.getItemCount());

        // 해당 item 재고 다시 증가
        item.setStock(item.getStock()+ cancelItem.getItemCount());

        // 해당 item의 판매량 감소
        item.setCount(item.getCount()-cancelItem.getItemCount());

        // 판매자 돈 감소
        cancelItem.getSaleItem().getSeller().setCoin(cancelItem.getSaleItem().getSeller().getCoin()- cancelItem.getItemTotalPrice());

        // 구매자 돈 증가
        cancelItem.getUser().setCoin(cancelItem.getUser().getCoin()+ cancelItem.getItemTotalPrice());

        // 해당 orderItem의 주문 상태 1로 변경 -> 주문 취소를 의미
        cancelItem.setIsCancel(cancelItem.getIsCancel()+1);

        // 해당 orderItem.getsaleItemId 로 saleItem 찾아서 판매 상태 1로 변경 -> 판매 취소를 의미
        cancelItem.getSaleItem().setIsCancel(cancelItem.getSaleItem().getIsCancel()+1);

        orderItemRepository.save(cancelItem);
        saleItemRepository.save(cancelItem.getSaleItem());
    }


}
