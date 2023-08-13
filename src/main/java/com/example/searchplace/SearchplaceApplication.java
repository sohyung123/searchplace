package com.example.searchplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SearchplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchplaceApplication.class, args);
	}

}
