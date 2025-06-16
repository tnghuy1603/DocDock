package com.docdock.group09.user_service;

import com.docdock.group09.user_service.entity.EmployeeEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.repository.EmployeeRepository;
import com.docdock.group09.user_service.utils.MockDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {
	private final EmployeeRepository employeeRepository;
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<EmployeeEntity> employees = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			employees.add(MockDataGenerator.generateMockEmployee());
		}
		employeeRepository.saveAll(employees);
	}
}
