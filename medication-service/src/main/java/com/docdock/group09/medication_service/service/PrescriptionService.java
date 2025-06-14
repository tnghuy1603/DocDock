package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request);
    PrescriptionResponse update();
    Page<PrescriptionResponse> getPrescriptions(PrescriptionGetRequest request);
    PrescriptionResponse getPrescriptionById(String id);
}
