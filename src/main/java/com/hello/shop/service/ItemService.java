package com.hello.shop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

	//상품 리스트 페이지용
	public Page<Item> allItemViewPage(Pageable pageable){
		return itemRepository.findAll(pageable);
	}

	//상품 수정
	@Transactional
	public void updateItem(Item item, Integer id, MultipartFile imgFile) throws Exception{

		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + imgFile.getOriginalFilename();
		File saveFile = new File(projectPath, fileName);
		imgFile.transferTo(saveFile);

		Item update = itemRepository.findItemById(id);
		update.setName(item.getName());
		update.setText(item.getText());
		update.setPrice(item.getPrice());
		update.setStock(item.getStock());
		update.setIsSoldout(item.getIsSoldout());
		update.setImgName(fileName);
		update.setImgPath("/files/"+fileName);
		itemRepository.save(update);
	}

	//상품 삭제
	@Transactional
	public void deleteItem(Integer id){
		itemRepository.deleteById(id);
	}

	// //상품 검색
	// public Page<Item> itemSearchList(String searchKeyword, Pageable pageable){
	// 	itemRepository.findByNa
	// }

}
