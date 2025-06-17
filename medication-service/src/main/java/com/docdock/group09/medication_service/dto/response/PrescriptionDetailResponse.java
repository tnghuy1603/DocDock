package com.docdock.group09.medication_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PrescriptionDetailResponse {
    private String id;
    private int quantity;
    private int dosage;
    private String note;
    private BigDecimal subTotal;
    private String name;
    private String description;
    private BigDecimal price;
}
