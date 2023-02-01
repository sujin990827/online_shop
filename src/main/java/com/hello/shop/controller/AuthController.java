package com.hello.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hello.shop.domain.User;
import com.hello.shop.dto.SignupDto;
import com.hello.shop.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

	private final AuthService authService;

	//로그인
	@GetMapping("/signin")
	public String SigninForm(){
		return "signin";
	}

	//회원가입
	@GetMapping("/signup")
	public String SignupForm(){
		return "signup";
	}

	@PostMapping("/signup")
	public String SignUp(SignupDto signupDto){
		User user = signupDto.toEntity();
		User userEntity = authService.signup(user);
		return "signin";
	}

}
