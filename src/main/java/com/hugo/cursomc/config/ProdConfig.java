package com.hugo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hugo.cursomc.services.DBService;
import com.hugo.cursomc.services.EmailService;
import com.hugo.cursomc.services.MockEmailService;

@Configuration	
@Profile("dev")
public class ProdConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if("create".equals(strategy)) {
			dbService.instantiateDatabase();
			return true;
		} else {
			return false;
		}
		
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
