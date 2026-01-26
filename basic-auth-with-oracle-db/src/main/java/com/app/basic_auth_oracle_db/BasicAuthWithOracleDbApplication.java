package com.app.basic_auth_oracle_db;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BasicAuthWithOracleDbApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("pass"));
		SpringApplication.run(BasicAuthWithOracleDbApplication.class, args);
	}

}
