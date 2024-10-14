package com.johnny.gamesales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GamesalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesalesApplication.class, args);
	}

}
