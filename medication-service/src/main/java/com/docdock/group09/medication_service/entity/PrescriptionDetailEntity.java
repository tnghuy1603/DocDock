package com.docdock.group09.medication_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescription_detail")
public class PrescriptionDetailEntity {
    @Id
    @UuidGenerator
    private String id;
    private int quantity;
    private int dosage;
    private String note;
    private BigDecimal price;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescription;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;
}
