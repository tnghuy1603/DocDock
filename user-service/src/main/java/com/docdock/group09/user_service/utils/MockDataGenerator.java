package com.docdock.group09.user_service.utils;

import com.docdock.group09.user_service.constant.UserRole;
import com.docdock.group09.user_service.entity.EmployeeEntity;
import com.docdock.group09.user_service.entity.PatientEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;
public class MockDataGenerator {
    private static final Faker faker = new Faker();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String DEFAULT_PASSWORD = "123456";
    public static UserEntity generateMockUser() {
        return UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(faker.name().fullName())
                .address(faker.address().fullAddress())
                .email(faker.internet().emailAddress())
                .role(UserRole.PATIENT)
                .password(faker.internet().password())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .dob(LocalDate.now().minusYears(faker.number().numberBetween(18, 60)))
                .build();
    }

    public static EmployeeEntity generateMockEmployee() {
        return EmployeeEntity.builder()
                .name(faker.name().fullName())
                .address(faker.address().fullAddress())
                .email(faker.internet().emailAddress())
                .role(UserRole.DOCTOR)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .phoneNumber(faker.phoneNumber().cellPhone())
                .dob(LocalDate.now().minusYears(faker.number().numberBetween(25, 65)))
                .specialty(faker.medical().diseaseName())
                .experience(faker.number().numberBetween(1, 40) + " years")
                .education(faker.university().name())
                .build();
    }
    public static PatientEntity generateMockPatient() {
        return PatientEntity.builder()
                .name(faker.name().fullName())
                .address(faker.address().fullAddress())
                .email(faker.internet().emailAddress())
                .role(UserRole.PATIENT)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .phoneNumber(faker.phoneNumber().cellPhone())
                .dob(LocalDate.now().minusYears(faker.number().numberBetween(18, 80)))


                .bloodType(faker.options().option("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"))
                .height(faker.number().numberBetween(150, 200))
                .allergies(faker.lorem().words(3).toString())
                .pastIllness(faker.lorem().sentence())
                .build();
    }
}
