package com.docdock.group09.medication_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "medication")
public class MedicationEntity {
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String description;
    private String dosageForm;
    private String category;
    private LocalDate expiryDate;
    private BigDecimal price;
    private int stockQuantity;
    @OneToMany(mappedBy = "medications")
    private List<PrescriptionDetailEntity> prescriptionDetails;
}
