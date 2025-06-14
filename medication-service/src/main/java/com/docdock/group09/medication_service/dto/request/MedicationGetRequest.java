package com.docdock.group09.medication_service.dto.request;

import com.docdock.group09.medication_service.constant.MedicationCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationGetRequest {
    private String name;
    private MedicationCategory category;
    private int limit = 8;
    private int offset = 0;
}
