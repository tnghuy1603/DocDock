package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.dto.request.MedicationRequest;
import com.docdock.group09.medication_service.dto.response.MedicationResponse;
import com.docdock.group09.medication_service.dto.response.MedicationStatsResponse;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import org.springframework.data.domain.Page;

public interface MedicationService {
    MedicationResponse getById(String id);
    Page<MedicationResponse> getByQuery(MedicationGetRequest request);
    MedicationResponse updateMedication(MedicationRequest request, String medicationId);
    MedicationResponse createMedication(MedicationRequest request);
    MedicationStatsResponse getStats();
}
