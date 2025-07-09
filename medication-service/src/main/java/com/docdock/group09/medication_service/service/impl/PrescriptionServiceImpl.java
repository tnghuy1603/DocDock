package com.docdock.group09.medication_service.service.impl;

import com.docdock.group09.medication_service.constant.PrescriptionStatus;
import com.docdock.group09.medication_service.constant.UserRole;
import com.docdock.group09.medication_service.dto.mapper.PrescriptionMapper;
import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.dto.request.UpdatePrescriptionStatus;
import com.docdock.group09.medication_service.dto.response.MedicalRecordInfo;
import com.docdock.group09.medication_service.dto.response.PrescriptionDetailResponse;
import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import com.docdock.group09.medication_service.dto.response.UserInfo;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import com.docdock.group09.medication_service.exception.MedicationServiceException;
import com.docdock.group09.medication_service.repository.MedicationRepository;
import com.docdock.group09.medication_service.repository.PrescriptionDetailRepository;
import com.docdock.group09.medication_service.repository.PrescriptionRepository;
import com.docdock.group09.medication_service.repository.spec.MedicationSpecification;
import com.docdock.group09.medication_service.repository.spec.PrescriptionSpecification;
import com.docdock.group09.medication_service.service.MedicalRecordClient;
import com.docdock.group09.medication_service.service.PrescriptionService;
import com.docdock.group09.medication_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final MedicationRepository medicationRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final UserServiceClient userServiceClient;
    private final MedicalRecordClient medicalRecordClient;

    @Transactional
    @Override
    public PrescriptionResponse prescribeMedication(CreatePrescriptionRequest request) {
        List<String> medicationIds = request.getPrescriptionDetails()
                .stream()
                .map(CreatePrescriptionRequest.PrescriptionDetailDTO::getMedicationId)
                .toList();
        MedicalRecordInfo medicalRecordInfo = medicalRecordClient.getMedicalRecordById(request.getMedicalRecordId()).getData();
        if (medicalRecordInfo == null) {
            throw MedicationServiceException.buildBadRequest("Can not find medication record id = " + request.getMedicalRecordId());
        }
        if (!medicalRecordInfo.getPatientId().equals(request.getPatientId())) {
            throw MedicationServiceException.buildBadRequest("patientId doesn't match patient info in medical record");
        }
        UserInfo patientInfo = userServiceClient.getUserInfo(request.getPatientId(), UserRole.PATIENT.toString()).getData();
        if (patientInfo == null) {
            throw MedicationServiceException.buildBadRequest(MessageFormat.format("PATIENT {0} not found", request.getPatientId()));
        }
        UserInfo doctorInfo = userServiceClient.getUserInfo(request.getDoctorId(), UserRole.DOCTOR.toString()).getData();
        if (doctorInfo == null) {
            throw MedicationServiceException.buildBadRequest(MessageFormat.format("PATIENT {0} not found", request.getPatientId()));
        }
        List<MedicationEntity> medicationEntities = medicationRepository.findByIdIn(medicationIds);
        if (medicationEntities.size() != medicationIds.size()) {
            throw MedicationServiceException.buildBadRequest("Can not found one or more medications");
        }

        Map<String, MedicationEntity> medicationMap = medicationEntities.stream()
                .collect(Collectors.toMap(MedicationEntity::getId, Function.identity()));

        List<PrescriptionDetailEntity> prescriptionDetailEntities = new ArrayList<>();
        List<CreatePrescriptionRequest.PrescriptionDetailDTO> detail = request.getPrescriptionDetails();
        BigDecimal total = BigDecimal.ZERO;
        for (CreatePrescriptionRequest.PrescriptionDetailDTO detailDTO : detail) {
            MedicationEntity medicationEntity = medicationMap.get(detailDTO.getMedicationId());
            if (detailDTO.getQuantity() > medicationEntity.getStockQuantity()) {
                throw MedicationServiceException.buildBadRequest(MessageFormat.format("Medicine {0} with id = {1} is out of stock", medicationEntity.getName(), medicationEntity.getId()));
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
                .medicalRecordId(medicalRecordInfo.getId())
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
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        Page<PrescriptionEntity> prescriptionEntityPage = prescriptionRepository.findAll(PrescriptionSpecification.filterPrescription(request), pageable);
        List<PrescriptionResponse> prescriptionResponses = prescriptionMapper.toModelList(prescriptionEntityPage.getContent());
        return new PageImpl<>(prescriptionResponses, pageable, prescriptionEntityPage.getTotalElements());
    }

    @Override
    public PrescriptionResponse getPrescriptionById(String id) {
        PrescriptionEntity prescriptionEntity = prescriptionRepository.findById(id)
                .orElseThrow(() -> MedicationServiceException.buildBadRequest(MessageFormat.format("Prescription with id {0} not found", id)));
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
                    .id(prescriptionDetailEntity.getId())
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

    @Override
    public PrescriptionResponse updateStatus(String id, UpdatePrescriptionStatus request) {
        PrescriptionEntity prescriptionEntity = prescriptionRepository.findById(id)
                .orElseThrow(() -> MedicationServiceException.buildBadRequest("Can not find prescription with id " + id));
        //TODO add logic later
        prescriptionEntity.setStatus(PrescriptionStatus.valueOf(request.getStatus()));
        prescriptionEntity.setUpdatedAt(LocalDateTime.now());
        prescriptionRepository.save(prescriptionEntity);
        return prescriptionMapper.toModel(prescriptionEntity);
    }


}
