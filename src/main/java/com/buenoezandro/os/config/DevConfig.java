package com.buenoezandro.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.buenoezandro.os.services.DBService;

@Profile(value = "dev")
@Configuration
public class DevConfig {

	private DBService dbService;

	@Value(value = "${spring.jpa.hibernate.ddl-auto}")
	private String ddl;

	@Autowired
	public DevConfig(DBService dbService) {
		this.dbService = dbService;
	}

	@Bean
	public boolean inserirDadosTeste() {
		if (ddl.equalsIgnoreCase("create")) {
			this.dbService.inserirDadosTeste();
		}
		return false;
	}

}
