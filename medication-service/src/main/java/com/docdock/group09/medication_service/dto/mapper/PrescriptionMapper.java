package com.docdock.group09.medication_service.dto.mapper;

import com.docdock.group09.medication_service.dto.response.PrescriptionResponse;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionResponse toModel(PrescriptionEntity entity);
    List<PrescriptionResponse> toModelList(List<PrescriptionEntity> entities);
}
