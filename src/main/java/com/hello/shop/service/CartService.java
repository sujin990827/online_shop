package com.hello.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.hello.shop.domain.Cart;
import com.hello.shop.domain.CartItem;
import com.hello.shop.domain.Item;
import com.hello.shop.domain.User;
import com.hello.shop.repository.CartItemRepository;
import com.hello.shop.repository.CartRepository;
import com.hello.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private final ItemRepository itemRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	//회원가입시 카트 하나 생성
	public void createCart(User user){
		Cart cart = Cart.createCart(user);
		cartRepository.save(cart);
	}

	//id로 해당 유저의 장바구니 찾기
	public Cart findUserCart(int userId){
		return cartRepository.findCartByUserId(userId);
	}

	//해당 유저가 담은 상품반 반환
	public List<CartItem> allUserCartView(Cart userCart){
		//유저 아이디 꺼냄
		int userCartId = userCart.getId();

		// id에 해당하는 유저가 담은 상품들 넣어둘 곳
		List<CartItem> UserCartItems = new ArrayList<>();

		// 유저 상관 없이 카트에 있는 상품 모두 불러오기
		List<CartItem> CartItems = cartItemRepository.findAll();

		for(CartItem cartItem : CartItems) {
			if(cartItem.getCart().getId() == userCartId) {
				UserCartItems.add(cartItem);
			}
		}
		return UserCartItems;
	}

	// 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
	public List<CartItem> findCartItemByItemId(int id) {

		List<CartItem> cartItems = cartItemRepository.findCartItemByItemId(id);

		return cartItems;
	}

	// 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
	public CartItem findCartItemById(int id) {

		CartItem cartItem = cartItemRepository.findCartItemById(id);

		return cartItem;
	}

	//장바구니 담기
	@Transactional
	public void addCart(User user, Item newItem, int amount) {

		// 유저 id로 해당 유저의 장바구니 찾기
		Cart cart = cartRepository.findByUserId(user.getId());

		// 장바구니가 존재하지 않는다면
		if (cart == null) {
			cart = Cart.createCart(user);
			cartRepository.save(cart);
		}

		Item item = itemRepository.findItemById(newItem.getId());
		CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

		// 상품이 장바구니에 존재하지 않는다면 카트상품 생성 후 추가
		if (cartItem == null) {
			cartItem = CartItem.createCartItem(cart, item, amount);
			cartItemRepository.save(cartItem);
		}

		// 상품이 장바구니에 이미 존재한다면 수량만 증가
		else {
			CartItem update = cartItem;
			update.setCart(cartItem.getCart());
			update.setItem(cartItem.getItem());
			update.addCount(amount);
			update.setCount(update.getCount());
			cartItemRepository.save(update);
		}

		// 카트 상품 총 개수 증가
		cart.setCount(cart.getCount()+amount);

	}

	//개별 상품 삭제
	public void cartItemDelete(int id){
		cartItemRepository.deleteById(id);
	}

	//전체 아이템 삭제
	public void	allCartItemDelete(int id){
		List<CartItem> all = cartItemRepository.findAll();

		for (CartItem cartItem : all) {
			if (cartItem.getCart().getUser().getId() == id){
				cartItem.getCart().setCount(0);
				cartItemRepository.deleteById(cartItem.getId());
			}
		}
	}

}
