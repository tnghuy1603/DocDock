package com.docdock.group09.medication_service.dto.response;

import com.docdock.group09.medication_service.constant.MedicationCategory;
import com.docdock.group09.medication_service.constant.MedicationDosageForm;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class MedicationResponse {
    private String id;
    private String name;
    private String description;
    private MedicationDosageForm dosageForm;
    private MedicationCategory category;
    private LocalDate expiryDate;
    private BigDecimal price;
    private int stockQuantity;
}
