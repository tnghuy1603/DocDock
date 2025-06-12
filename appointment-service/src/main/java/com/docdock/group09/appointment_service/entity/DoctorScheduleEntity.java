package com.docdock.group09.appointment_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "doctor_schedule")
public class DoctorScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
