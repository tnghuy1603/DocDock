package com.docdock.group09.user_service.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PatientEntity extends UserEntity {
    private String bloodType;
    private int height;
    private String allergies;
    private String pastIllness;
}
