package com.interblocks.istate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.interblocks.istate.repository")
public class IStateApplication {
	public static void main(String[] args) {
		SpringApplication.run(IStateApplication.class, args);
	}
}
