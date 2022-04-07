package com.example.userservice.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.vo.Greeting;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
	private final Environment env;
	
	private final Greeting gretting;
		
	@GetMapping("/health_check")
	public String status() {
		return "It's Working in User Service";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
//		return env.getProperty("greeting.message");		// application.yml 파일에서 값 읽기
		return gretting.getMessage();
	}

}
