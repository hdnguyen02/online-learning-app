package com.hdnguyen;

import com.hdnguyen.entity.Deck;
import com.hdnguyen.service.AuthService;
import com.hdnguyen.service.DeckService;
import com.hdnguyen.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.List;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
		@Bean
		CommandLineRunner commandLineRunner (RoleService roleService, AuthService authService, DeckService deckService) {
			return arg -> {
				roleService.initRoles();
				authService.initUser();
				deckService.initDeck();

		};
	}
}



