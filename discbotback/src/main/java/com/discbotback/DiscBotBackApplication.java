package com.discbotback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.discbotback.upload.StorageService;

@SpringBootApplication
public class DiscBotBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscBotBackApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
	

}
