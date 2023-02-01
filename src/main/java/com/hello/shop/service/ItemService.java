package com.hello.shop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hello.shop.domain.Item;
import com.hello.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	private final ItemRepository itemRepository;

	//저장
	public void saveItem(Item item, MultipartFile imgFile) throws Exception{
		String originalFilename = imgFile.getOriginalFilename();
		String imgName="";
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

		//UUID를 이용하여 파일명 새로 생성
		//UUID - 서로 다른 객체를 구별하기 위한 클래스
		UUID uuid = UUID.randomUUID();

		String savedFileName = uuid + "_" + originalFilename;
		imgName = savedFileName;

		File saveFile = new File(projectPath, imgName);
		imgFile.transferTo(saveFile);

		item.setImgName(imgName);
		item.setImgPath("/files" + imgName);

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
