package com.docdock.group09.medical_record_service.service;

import com.docdock.group09.medical_record_service.dto.request.CreateMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.request.FilterMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.response.MedicalRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordResponse createRecord(CreateMedicalRecordRequest request);
    Page<MedicalRecordResponse> filter(FilterMedicalRecordRequest request);

}
