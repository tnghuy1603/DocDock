package com.docdock.group09.medication_service.service.impl;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import com.docdock.group09.medication_service.repository.MedicationRepository;
import com.docdock.group09.medication_service.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    @Override
    public MedicationEntity getById(String id) {
        return medicationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<MedicationEntity> getByQuery(MedicationGetRequest request) {
        return List.of();
    }

    @Override
    public MedicationEntity updateMedication(MedicationEntity medicationEntity) {
        return null;
    }
}
