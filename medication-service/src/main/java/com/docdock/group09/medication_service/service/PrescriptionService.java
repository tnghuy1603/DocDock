package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request);
    PrescriptionResponse update();
    List<PrescriptionResponse> getPrescriptions(PrescriptionGetRequest request);
}
