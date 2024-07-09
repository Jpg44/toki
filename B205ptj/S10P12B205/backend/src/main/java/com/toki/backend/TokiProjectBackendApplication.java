package com.toki.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.toki.backend.auth.repository"})
@EntityScan(basePackages = {"com.toki.backend.auth.entity"})
@EnableJpaAuditing
@SpringBootApplication
public class TokiProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokiProjectBackendApplication.class, args);
	}

}
