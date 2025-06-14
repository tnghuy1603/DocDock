package com.docdock.group09.medication_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class MedicationRequest {
    private String name;
    private String description;
    private String dosageForm;
    private String category;
    private LocalDate expiryDate;
    private BigDecimal price;
    private int stockQuantity;
}
