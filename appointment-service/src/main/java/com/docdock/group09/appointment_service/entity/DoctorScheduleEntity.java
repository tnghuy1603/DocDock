package com.docdock.group09.appointment_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "doctor_schedule")
public class DoctorScheduleEntity {
    @Id
    @UuidGenerator
    private String id;
    private String doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
