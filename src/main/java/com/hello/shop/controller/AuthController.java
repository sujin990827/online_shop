package com.hello.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hello.shop.domain.user.User;
import com.hello.shop.dto.SignupDto;
import com.hello.shop.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "signin";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupDto signupDto) {
        // User에 signupDto 넣음
        User user = signupDto.toEntity();

        User userEntity = authService.signup(user);
        System.out.println(userEntity);

        return "signin";
    }
}
