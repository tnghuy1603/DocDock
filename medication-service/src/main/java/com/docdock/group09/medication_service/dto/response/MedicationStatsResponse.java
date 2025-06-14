package com.docdock.group09.medication_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class MedicationStatsResponse {
    private int numberOfMedicines;
    private int numberOfExpireSoon;
    private int numberOfLowStock;
}
