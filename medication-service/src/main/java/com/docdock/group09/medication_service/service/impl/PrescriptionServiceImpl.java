package com.docdock.group09.medication_service.service.impl;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import com.docdock.group09.medication_service.dto.mapper.PrescriptionMapper;
import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.response.PrescriptionDetailResponse;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import com.docdock.group09.medication_service.repository.MedicationRepository;
import com.docdock.group09.medication_service.repository.PrescriptionDetailRepository;
import com.docdock.group09.medication_service.repository.PrescriptionRepository;
import com.docdock.group09.medication_service.repository.spec.MedicationSpecification;
import com.docdock.group09.medication_service.repository.spec.PrescriptionSpecification;
import com.docdock.group09.medication_service.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final MedicationRepository medicationRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Transactional
    @Override
    public PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request) {
        List<String> medicationIds = request.getPrescriptionDetails()
                .stream()
                .map(CreatePrescriptionRequest.PrescriptionDetailDTO::getMedicationId)
                .toList();
        List<MedicationEntity> medicationEntities = medicationRepository.findByIdIn(medicationIds);
        if (medicationEntities.size() != medicationIds.size()) {
            throw new RuntimeException("Bad request");
        }
        //TODO check doctor and patient id
        Map<String, MedicationEntity> medicationMap = medicationEntities.stream()
                .collect(Collectors.toMap(MedicationEntity::getId, Function.identity()));

        List<PrescriptionDetailEntity> prescriptionDetailEntities = new ArrayList<>();
        List<CreatePrescriptionRequest.PrescriptionDetailDTO> detail = request.getPrescriptionDetails();
        BigDecimal total = BigDecimal.ZERO;
        for (CreatePrescriptionRequest.PrescriptionDetailDTO detailDTO : detail) {
            MedicationEntity medicationEntity = medicationMap.get(detailDTO.getMedicationId());
            if (detailDTO.getQuantity() > medicationEntity.getStockQuantity()) {
                throw new RuntimeException("Bad request");
            }
            medicationEntity.setStockQuantity(medicationEntity.getStockQuantity() - detailDTO.getQuantity());
            PrescriptionDetailEntity prescriptionDetailEntity = new PrescriptionDetailEntity();
            prescriptionDetailEntity.setMedication(medicationEntity);
            prescriptionDetailEntity.setDosage(detailDTO.getDosage());
            prescriptionDetailEntity.setPrice(medicationEntity.getPrice());
            prescriptionDetailEntity.setNote(detailDTO.getNote());
            prescriptionDetailEntity.setSubTotal(medicationEntity.getPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity())));
            prescriptionDetailEntity.setQuantity(detailDTO.getQuantity());
            total = total.add(prescriptionDetailEntity.getSubTotal());
            prescriptionDetailEntities.add(prescriptionDetailEntity);
        }
        PrescriptionEntity prescriptionEntity = PrescriptionEntity.builder()
                .note(request.getNote())
                .status(PrescriptionStatus.NOT_TAKEN)
                .totalPrice(total)
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        prescriptionEntity = prescriptionRepository.save(prescriptionEntity);
        for (PrescriptionDetailEntity prescriptionDetailEntity : prescriptionDetailEntities) {
            prescriptionDetailEntity.setPrescription(prescriptionEntity);
        }
        prescriptionDetailRepository.saveAll(prescriptionDetailEntities);
        medicationRepository.saveAll(medicationEntities);
        //TODO send notification
        return prescriptionMapper.toModel(prescriptionEntity);
    }

    @Override
    public PrescriptionResponse update() {
        return null;
    }

    @Override
    public Page<PrescriptionResponse> getPrescriptions(PrescriptionGetRequest request) {
        Pageable pagable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        Page<PrescriptionEntity> prescriptionEntityPage = prescriptionRepository.findAll(PrescriptionSpecification.filterPrescription(request), pagable);
        List<PrescriptionResponse> prescriptionResponses = prescriptionMapper.toModelList(prescriptionEntityPage.getContent());
        return new PageImpl<>(prescriptionResponses, pagable, prescriptionEntityPage.getTotalElements());
    }

    @Override
    public PrescriptionResponse getPrescriptionById(String id) {
        PrescriptionEntity prescriptionEntity = prescriptionRepository.findById(id).orElse(null);
        return prescriptionMapper.toModel(prescriptionEntity);
    }

    @Override
    public List<PrescriptionDetailResponse> getPrescriptionDetails(String prescriptionId) {
        List<PrescriptionDetailEntity> detailEntities = prescriptionDetailRepository.findByPrescription_Id(prescriptionId);
        List<PrescriptionDetailResponse> result = new ArrayList<>();
        for (PrescriptionDetailEntity prescriptionDetailEntity : detailEntities) {
            MedicationEntity medicationEntity = prescriptionDetailEntity.getMedication();
            PrescriptionDetailResponse prescriptionDetailResponse = PrescriptionDetailResponse
                    .builder()
                    .name(medicationEntity.getName())
                    .price(prescriptionDetailEntity.getPrice())
                    .quantity(prescriptionDetailEntity.getQuantity())
                    .description(medicationEntity.getDescription())
                    .note(prescriptionDetailEntity.getNote())
                    .dosage(prescriptionDetailEntity.getDosage())
                    .subTotal(prescriptionDetailEntity.getSubTotal())
                    .build();
            result.add(prescriptionDetailResponse);
        }
        //TODO pagination
        return result;
    }


}
