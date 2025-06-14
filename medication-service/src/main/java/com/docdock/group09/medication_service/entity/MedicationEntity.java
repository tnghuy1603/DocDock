package com.docdock.group09.medication_service.entity;

import com.docdock.group09.medication_service.constant.MedicationCategory;
import com.docdock.group09.medication_service.constant.MedicationDosageForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

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
    @UuidGenerator
    private String id;
    private String name;
    private String description;
    private MedicationDosageForm dosageForm;
    @Enumerated(EnumType.STRING)
    private MedicationCategory category;
    private LocalDate expiryDate;
    private BigDecimal price;
    private int stockQuantity;
    @OneToMany(mappedBy = "medication")
    private List<PrescriptionDetailEntity> prescriptionDetails;
}
