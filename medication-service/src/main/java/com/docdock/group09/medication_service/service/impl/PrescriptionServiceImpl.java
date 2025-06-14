package com.docdock.group09.medication_service.service.impl;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import com.docdock.group09.medication_service.repository.MedicationRepository;
import com.docdock.group09.medication_service.repository.PrescriptionDetailRepository;
import com.docdock.group09.medication_service.repository.PrescriptionRepository;
import com.docdock.group09.medication_service.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    @Override
    public PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request) {
        List<String> medicationIds = request.getPrescriptionDetails()
                .stream()
                .map(detail -> detail.getMedicationId())
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
            PrescriptionDetailEntity prescriptionDetailEntity = new PrescriptionDetailEntity();
            prescriptionDetailEntity.setMedication(medicationEntity);
            prescriptionDetailEntity.setDosage(detailDTO.getDosage());
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
        //TODO send notification

        return null;

    }

    @Override
    public PrescriptionResponse update() {
        return null;
    }

    @Override
    public List<PrescriptionResponse> getPrescriptions(PrescriptionGetRequest request) {
        return List.of();
    }
}
