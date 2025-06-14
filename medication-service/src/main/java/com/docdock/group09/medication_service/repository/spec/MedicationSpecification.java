package com.docdock.group09.medication_service.repository.spec;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.entity.MedicationEntity;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MedicationSpecification {
    public static Specification<MedicationEntity> filterMedication(MedicationGetRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(request.getName())) {
                predicates.add(cb.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
            if (request.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), request.getCategory()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
