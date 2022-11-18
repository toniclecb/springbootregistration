package com.toniclecb.springbootregistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootRegistrationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRegistrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("running method run()...");
		System.out.println("Here you can make various tasks like we will do in the sequence (database creation)!");
	}

}
