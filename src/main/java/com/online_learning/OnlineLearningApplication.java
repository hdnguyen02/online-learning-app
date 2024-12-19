package com.online_learning;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.online_learning.service.InitService;

@SpringBootApplication
public class OnlineLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLearningApplication.class, args);
	}
}
