package com.hello.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder encoder(){

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();

		http.authorizeRequests()
			.antMatchers("/", "/main/**").authenticated() //인증 필요
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/signin") //인증 필요한 주소로 접속하면 이 주소로 이동시킴
			.loginProcessingUrl("/signin") //스프링 시큐리티가 로그인 자동 진행
			.defaultSuccessUrl("/main"); //로그인 성공시 "/"로 이동

	}

}
