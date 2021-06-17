package com.buenoezandro.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.buenoezandro.os.services.DBService;

@Profile(value = "test")
@Configuration
public class TesteConfig {

	private DBService dbService;

	@Autowired
	public TesteConfig(DBService dbService) {
		this.dbService = dbService;
	}

	@Bean
	public void inserirDadosTeste() {
		this.dbService.inserirDadosTeste();
	}

}
