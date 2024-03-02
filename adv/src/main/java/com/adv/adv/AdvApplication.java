package com.adv.adv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdvApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvApplication.class, args);
		String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);
	}

}
