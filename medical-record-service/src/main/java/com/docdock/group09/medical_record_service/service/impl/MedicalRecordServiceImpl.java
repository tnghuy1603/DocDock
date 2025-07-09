package com.docdock.group09.medical_record_service.service.impl;

import com.docdock.group09.medical_record_service.dto.mapper.MedicalRecordMapper;
import com.docdock.group09.medical_record_service.dto.request.CreateMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.request.FilterMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.response.MedicalRecordResponse;
import com.docdock.group09.medical_record_service.dto.response.UserInfo;
import com.docdock.group09.medical_record_service.entity.MedicalRecordEntity;
import com.docdock.group09.medical_record_service.exception.MedicalRecordServiceException;
import com.docdock.group09.medical_record_service.repository.MedicalRecordRepository;
import com.docdock.group09.medical_record_service.repository.spec.MedicalRecordSpecification;
import com.docdock.group09.medical_record_service.service.MedicalRecordService;
import com.docdock.group09.medical_record_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final UserServiceClient userServiceClient;
    @Override
    public MedicalRecordResponse createRecord(CreateMedicalRecordRequest request) {
        UserInfo patientInfo = userServiceClient.getUserInfo(request.getPatientId(), "PATIENT").getData();
        if (patientInfo == null) {
            throw new RuntimeException("Not found patient");
        }
        UserInfo doctorInfo = userServiceClient.getUserInfo(request.getDoctorId(), "DOCTOR").getData();
        if (doctorInfo == null) {
            throw new RuntimeException("Not found doctor");
        }
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.toEntity(request);
        medicalRecordEntity.setPatientPhoneNumber(patientInfo.getPhoneNumber());
        medicalRecordEntity.setPatientName(patientInfo.getName());
        medicalRecordEntity.setDoctorName(doctorInfo.getName());
        medicalRecordEntity.setCreatedAt(LocalDateTime.now());
        medicalRecordEntity.setUpdatedAt(LocalDateTime.now());
        medicalRecordEntity = medicalRecordRepository.save(medicalRecordEntity);
        return medicalRecordMapper.toModel(medicalRecordEntity);
    }

    @Override
    public Page<MedicalRecordResponse> filter(FilterMedicalRecordRequest request) {
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        Page<MedicalRecordEntity> medicalRecordEntities = medicalRecordRepository.findAll(MedicalRecordSpecification.filter(request), pageable);
        List<MedicalRecordResponse> medicalRecordResponses = medicalRecordMapper.toModelList(medicalRecordEntities.getContent());
        return new PageImpl<>(medicalRecordResponses,pageable,medicalRecordEntities.getTotalElements());
    }

    @Override
    public MedicalRecordResponse getById(String id) {
        MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.findById(id)
                .orElseThrow(() -> MedicalRecordServiceException.buildBadRequest("Not found medical record" + id));
        return medicalRecordMapper.toModel(medicalRecordEntity);
    }
}
