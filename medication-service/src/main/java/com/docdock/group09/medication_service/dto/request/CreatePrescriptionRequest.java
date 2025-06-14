package com.docdock.group09.medication_service.dto.request;

import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePrescriptionRequest {
    private String patientId;
    private String doctorId;
    private String note;
    private List<PrescriptionDetailDTO> prescriptionDetails;
    @Getter
    @Setter
    public static class PrescriptionDetailDTO {
        private String medicationId;
        private int quantity;
        private int dosage;
        private String note;
    }
}
