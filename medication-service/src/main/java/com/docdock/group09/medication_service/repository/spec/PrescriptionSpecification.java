package com.docdock.group09.medication_service.repository.spec;

import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class PrescriptionSpecification {
    public static Specification<PrescriptionEntity> filterPrescription(PrescriptionGetRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(request.getDoctorId())) {
                predicates.add(cb.equal(root.get("doctorId"), request.getDoctorId()));
            }
            if (StringUtils.isNotEmpty(request.getPatientId())) {
                predicates.add(cb.equal(root.get("patientId"), request.getPatientId()));
            }
            if (StringUtils.isNotEmpty(request.getMedicalRecordId())) {
                predicates.add(cb.equal(root.get("medicalRecordId"), request.getMedicalRecordId()));
            }
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
