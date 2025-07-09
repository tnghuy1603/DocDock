package com.docdock.group09.medical_record_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medical_record")
public class MedicalRecordEntity {
    @Id
    @UuidGenerator
    private String id;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String patientPhoneNumber;
    private String treatment;
    private String diagnosis;
    private String description;
    private LocalDate visitDate;
    @Enumerated(EnumType.STRING)
    private VisitType visitType;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
