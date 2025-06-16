package com.docdock.group09.user_service;

import com.docdock.group09.user_service.entity.EmployeeEntity;
import com.docdock.group09.user_service.entity.PatientEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.repository.EmployeeRepository;
import com.docdock.group09.user_service.repository.PatientRepository;
import com.docdock.group09.user_service.utils.MockDataGenerator;
import jakarta.transaction.Transactional;
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
	private final PatientRepository patientRepository;
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		List<EmployeeEntity> employees = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			employees.add(MockDataGenerator.generateMockEmployee());
		}
		employeeRepository.saveAll(employees);
		List<PatientEntity> patients = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			patients.add(MockDataGenerator.generateMockPatient());
		}
		patientRepository.saveAll(patients);
	}
}
