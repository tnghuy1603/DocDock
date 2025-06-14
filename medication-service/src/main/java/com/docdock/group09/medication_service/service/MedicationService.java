package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.entity.MedicationEntity;

import java.util.List;

public interface MedicationService {
    MedicationEntity getById(String id);
    List<MedicationEntity> getByQuery(MedicationGetRequest request);
    MedicationEntity updateMedication(MedicationEntity medicationEntity);
}
