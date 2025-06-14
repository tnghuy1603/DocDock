package com.docdock.group09.medication_service.dto.response;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Builder
@Getter
public class PrescriptionResponse {
    private String id;
    private String note;
    private String patientId;
    private String doctorId;
    private BigDecimal totalPrice;
    private String notes;
    private PrescriptionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
