package com.hello.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hello.shop.domain.Item;
import com.hello.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private final ItemRepository itemRepository;

	//저장
	public void saveItem(Item item){
		itemRepository.save(item);
	}

	//단건 조회
	public Item findItemById(Integer id){
		return itemRepository.findById(id).get();
	}

	//모두 조회
	public List<Item> findAllItem(){
		return itemRepository.findAll();
	}

	//상품 수정
	public void updateItem(Item item, Integer id){
		Item itemId = itemRepository.findItemById(id);
		itemId.setName(item.getName());
		itemId.setText(item.getText());
		itemId.setPrice(item.getPrice());
		itemId.setStock(item.getStock());
		itemRepository.save(itemId);
	}

	//상품 삭제
	public void deleteItem(Integer id){
		itemRepository.deleteById(id);
	}

}
