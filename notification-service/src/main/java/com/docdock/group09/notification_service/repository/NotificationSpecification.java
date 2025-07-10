package com.docdock.group09.notification_service.repository;

import com.docdock.group09.notification_service.dto.request.NotificationFilterRequest;
import com.docdock.group09.notification_service.entity.NotificationEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NotificationSpecification {
    public static Specification<NotificationEntity> filterNotification(NotificationFilterRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(request.getUserId())) {
                predicates.add(cb.equal(root.get("receiverId"), request.getUserId()));
            }

            if (StringUtils.isNotBlank(request.getChannel())) {
                predicates.add(cb.equal(root.get("channel"), request.getChannel()));
            }

            if (StringUtils.isNotBlank(request.getType())) {
                predicates.add(cb.equal(root.get("type"), request.getType()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
