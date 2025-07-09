package com.docdock.group09.medication_service.entity;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "prescription")
public class PrescriptionEntity {
    @Id
    @UuidGenerator
    private String id;
    private String patientId;
    private String doctorId;
    private String medicalRecordId;
    private BigDecimal totalPrice;
    private String note;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    @OneToMany(mappedBy = "prescription")
    private List<PrescriptionDetailEntity> prescriptionDetails;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
