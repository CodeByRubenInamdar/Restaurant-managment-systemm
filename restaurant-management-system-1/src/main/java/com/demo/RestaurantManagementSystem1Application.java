package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = "com.demo")
public class RestaurantManagementSystem1Application {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementSystem1Application.class, args);
	}

}