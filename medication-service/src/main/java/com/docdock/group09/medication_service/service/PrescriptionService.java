package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.request.UpdatePrescriptionStatus;
import com.docdock.group09.medication_service.dto.response.PrescriptionDetailResponse;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request);
    PrescriptionResponse update();
    Page<PrescriptionResponse> getPrescriptions(PrescriptionGetRequest request);
    PrescriptionResponse getPrescriptionById(String id);
    List<PrescriptionDetailResponse> getPrescriptionDetails(String prescriptionId);
    PrescriptionResponse updateStatus(String id, UpdatePrescriptionStatus request);
}
