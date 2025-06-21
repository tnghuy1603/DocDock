package com.docdock.group09.medical_record_service.dto.mapper;

import com.docdock.group09.medical_record_service.dto.request.CreateMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.response.MedicalRecordResponse;
import com.docdock.group09.medical_record_service.entity.MedicalRecordEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecordResponse toModel(MedicalRecordEntity entity);
    List<MedicalRecordResponse> toModelList(List<MedicalRecordEntity> entity);
    MedicalRecordEntity toEntity(CreateMedicalRecordRequest request);
}
