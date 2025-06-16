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
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(request.getSearchKeyword())) {
                String keyword = "%" + request.getSearchKeyword() + "%";
                Predicate namePredicate = cb.like(root.get("name"), keyword);
                Predicate idPredicate = cb.like(root.get("id").as(String.class), keyword);
                Predicate phonePredicate = cb.like(root.get("phoneNumber"), keyword);
                predicates.add(cb.or(namePredicate, idPredicate, phonePredicate));
            }
            if (!request.getRoles().isEmpty()) {
                predicates.add(root.get("role").in(request.getRoles()));
            }
            if (StringUtils.isNotEmpty(request.getSpecialty())) {
                Join<UserEntity, EmployeeEntity> employeeJoin = root.join("employeeEntity", JoinType.LEFT);
                predicates.add(cb.equal(employeeJoin.get("specialty"), request.getSpecialty()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
