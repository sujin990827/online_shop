package com.hello.shop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)  //유저이름 중복 불가능
	private String username;

	private String password;
	private String name;
	private String email;
	private String address;
	private String phone;
	private String role; //권한

	private int coin; //구매자: 충전한 돈, 판매자: 수익

	// 판매자가 가지고 있는 상품들
	@OneToMany(mappedBy = "seller")
	private List<Item> items = new ArrayList<>();

	// 구매자의 장바구니
	@OneToOne(mappedBy = "user")
	private Cart cart;

	private LocalDateTime createDate; // 생성날짜

	@PrePersist // 디비에 삽입 되기 전에 실행됨.
	public void createDate(){
		this.createDate = LocalDateTime.now();
	}

}
