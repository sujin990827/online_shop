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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	private int count;

	@OneToMany(mappedBy = "cart")
	private List<CartItem> cartItems = new ArrayList<>();

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate createDate; //날짜

	@PrePersist
	public void createDate(){
		this.createDate = LocalDate.now();
	}

	public static Cart createCart(User user){
		Cart cart = new Cart();
		cart.setCount(0);
		cart.setUser(user);
		return cart;
	}
}
