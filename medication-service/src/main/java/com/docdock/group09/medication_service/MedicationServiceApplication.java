package com.docdock.group09.medication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicationServiceApplication.class, args);
	}

}
