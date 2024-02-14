package com.assignment.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NetherlandsParkingSystemApp {

	public static void main(String[] args) {
		SpringApplication.run(NetherlandsParkingSystemApp.class, args);
	}

}