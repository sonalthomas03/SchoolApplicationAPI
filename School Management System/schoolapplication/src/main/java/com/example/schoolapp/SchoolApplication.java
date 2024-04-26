package com.example.schoolapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SchoolApplication {

	public static void main(String[] args) {

		SpringApplication.run(SchoolApplication.class, args);
	}




}
