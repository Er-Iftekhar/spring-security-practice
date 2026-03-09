package com.app.auth_dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthDaoApplication {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("pass"));
		SpringApplication.run(AuthDaoApplication.class, args);
	}

}
