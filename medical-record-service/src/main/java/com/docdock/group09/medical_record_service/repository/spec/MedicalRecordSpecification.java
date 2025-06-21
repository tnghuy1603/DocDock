package com.docdock.group09.medical_record_service.repository.spec;

import com.docdock.group09.medical_record_service.dto.request.FilterMedicalRecordRequest;
import com.docdock.group09.medical_record_service.entity.MedicalRecordEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MedicalRecordSpecification {
    public static Specification<MedicalRecordEntity> filter(FilterMedicalRecordRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(request.getDoctorId())) {
                predicates.add(criteriaBuilder.equal(root.get("doctorId"), request.getDoctorId()));
            }
            if (StringUtils.isNotEmpty(request.getPatientId())) {
                predicates.add(criteriaBuilder.equal(root.get("patientId"), request.getPatientId()));
            }
            if (StringUtils.isNotEmpty(request.getDiagnosis())) {
                predicates.add(criteriaBuilder.like(root.get("diagnosis"),'%' + request.getDiagnosis() + "%"));
            }
            if (StringUtils.isNotBlank(request.getPatientName())) {
                predicates.add(criteriaBuilder.like(root.get("patientName"),'%' + request.getPatientName() + "%"));
            }
            if (StringUtils.isNotBlank(request.getDoctorName())) {
                predicates.add(criteriaBuilder.like(root.get("doctorName"),'%' + request.getDoctorName() + "%"));
            }
            if (request.getVisitType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("visitType"), request.getVisitType()));
            }
            if (request.getFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("visitDate"), request.getFrom()));
            }
            if (request.getTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("visitDate"), request.getTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
