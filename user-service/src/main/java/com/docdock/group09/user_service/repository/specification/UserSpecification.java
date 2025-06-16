package com.docdock.group09.user_service.repository.specification;

import com.docdock.group09.user_service.dto.request.UserGetRequest;
import com.docdock.group09.user_service.entity.EmployeeEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> filterUser(UserGetRequest request) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), request.getRole()));
            }

            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(request.getPhoneNumber())) {
                predicates.add(cb.like(root.get("phoneNumber"), "%" + request.getPhoneNumber() + "%"));
            }

            if (StringUtils.isNotBlank(request.getUserId())) {
                predicates.add(cb.equal(root.get("id"), request.getUserId()));
            }

            if (StringUtils.isNotBlank(request.getEmail())) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getSpecialty() != null && !request.getSpecialty().isBlank()) {
                Join<UserEntity, EmployeeEntity> employeeJoin = root.join("employeeEntity", JoinType.LEFT);
                predicates.add(cb.equal(employeeJoin.get("specialty"), request.getSpecialty()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
