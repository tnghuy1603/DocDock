package com.docdock.group09.medication_service.dto.mapper;

import com.docdock.group09.medication_service.dto.request.MedicationRequest;
import com.docdock.group09.medication_service.dto.response.MedicationResponse;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    MedicationResponse toModel(MedicationEntity entity);
    MedicationEntity toEntity(MedicationRequest response);
    List<MedicationResponse> toModelList(List<MedicationEntity> response);
}
