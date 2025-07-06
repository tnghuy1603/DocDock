package com.docdock.group09.medication_service.service.impl;

import com.docdock.group09.medication_service.dto.mapper.MedicationMapper;
import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.dto.request.MedicationRequest;
import com.docdock.group09.medication_service.dto.response.MedicationResponse;
import com.docdock.group09.medication_service.dto.response.MedicationStatsResponse;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import com.docdock.group09.medication_service.exception.MedicationServiceException;
import com.docdock.group09.medication_service.repository.MedicationRepository;
import com.docdock.group09.medication_service.repository.spec.MedicationSpecification;
import com.docdock.group09.medication_service.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    @Override
    public MedicationResponse getById(String id) {
        MedicationEntity entity = medicationRepository.findById(id).orElseThrow(() -> MedicationServiceException.buildBadRequest("Not found medication with that id"));
        return medicationMapper.toModel(entity);
    }

    @Override
    public Page<MedicationResponse> getByQuery(MedicationGetRequest request) {
        Sort sortByNameDesc = Sort.by(Sort.Direction.DESC, "name");
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit(), sortByNameDesc);
        Page<MedicationEntity> medicationEntityPage = medicationRepository.findAll(MedicationSpecification.filterMedication(request), pageable);
        List<MedicationResponse> responseList = medicationMapper.toModelList(medicationEntityPage.getContent());
        return new PageImpl<>(responseList, pageable, medicationEntityPage.getTotalElements());
    }


    @Override
    public MedicationResponse updateMedication(MedicationRequest request, String medicationId) {
        MedicationEntity existingEntity = medicationRepository.findById(medicationId)
                .orElseThrow(() -> MedicationServiceException.buildBadRequest(MessageFormat.format("Medication with id {0} not found", medicationId)));
        existingEntity.setName(request.getName());
        existingEntity.setDescription(request.getDescription());
        existingEntity = medicationRepository.save(existingEntity);
        return  medicationMapper.toModel(existingEntity);
    }

    @Override
    public MedicationResponse createMedication(MedicationRequest request) {
        MedicationEntity entity = medicationMapper.toEntity(request);
        entity = medicationRepository.save(entity);
        return medicationMapper.toModel(entity);
    }

    @Override
    public MedicationStatsResponse getStats() {
        Object[] stats = medicationRepository.getMedicineStats(LocalDate.now().plusDays(7), 10).get(0);
        int totalCount = ((Number) stats[0]).intValue();
        int expireSoonCount = ((Number) stats[1]).intValue();
        int lowStockCount = ((Number) stats[2]).intValue();
        return MedicationStatsResponse.builder()
                .numberOfMedicines(totalCount)
                .numberOfExpireSoon(expireSoonCount)
                .numberOfLowStock(lowStockCount)
                .build();
    }

}
