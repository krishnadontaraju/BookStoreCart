package com.bookstore.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookStoreCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreCartApplication.class, args);
	}

}
