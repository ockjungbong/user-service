package com.example.userservice.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Greeting {
	@Value("${gretting.message}")
	private String message;

}
