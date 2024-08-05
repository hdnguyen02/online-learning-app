package com.online_learning;

import com.online_learning.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLearningApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (RoleService roleService, AuthService authService, DeckService deckService, GroupService groupService, CommonDeckService commonDeckService, InvoiceService invoiceService) {
		return arg -> {
			roleService.initRoles();
			authService.initUser();
			deckService.initDeck();
			groupService.initGroup();
			commonDeckService.initCommonDeck();
			invoiceService.initInvoice();
		};
	}
}
