package com.hello.shop.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String text;
	private int price;
	private int count;
	private int stock;
	private String photo;

	private boolean isSoldout; //(0:판매중, 1:품절)

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seller_id")
	private User seller; // 판매자 아이디

	@OneToMany(mappedBy = "item")
	private List<CartItem> cartItems = new ArrayList<>();

	private String imgName; //이미지 파일명
	private String imgPath; //이미지 조회 경로

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate createDate; // 상품 등록 날짜

	@PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
	public void createDate() {
		this.createDate = LocalDate.now();
	}


}
