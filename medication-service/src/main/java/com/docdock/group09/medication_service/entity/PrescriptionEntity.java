package com.docdock.group09.medication_service.entity;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescription")
public class PrescriptionEntity {
    @Id
    @GeneratedValue
    private String id;

    private String patientId;
    private String doctorId;
    private BigDecimal totalPrice;
    private String notes;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    @OneToMany(mappedBy = "prescription")
    private List<PrescriptionDetailEntity> prescriptionDetails;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
