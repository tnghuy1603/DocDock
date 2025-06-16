package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentStatusCount;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepositoryCustomImpl implements AppointmentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<AppointmentStatusCount> countByFilter(FilterAppointmentRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<AppointmentEntity> root = cq.from(AppointmentEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNoneEmpty(request.getDoctorId())) {
            predicates.add(cb.equal(root.get("doctorId"), request.getDoctorId()));
        }
        if (StringUtils.isNotEmpty(request.getPatientId())) {
            predicates.add(cb.equal(root.get("patientId"), request.getPatientName()));
        }
        if (request.getAppointmentDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("date"), request.getAppointmentDate()));
        }
        if (ObjectUtils.isNotEmpty(request.getPatientIds())) {
            predicates.add(cb.in(root.get("patientId").in(request.getPatientIds())));
        }

        if (ObjectUtils.isNotEmpty(request.getReason())) {
            predicates.add(cb.like(root.get("reason"), "%" + request.getReason() + "%"));
        }

        if (request.getType() != null) {
            predicates.add(cb.equal(root.get("type"), request.getType()));
        }
        cq.multiselect(
                        root.get("status").alias("status"),
                        cb.count(root).alias("count")
                )
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(root.get("status"));
        List<Tuple> tuples = entityManager.createQuery(cq).getResultList();
        return tuples.stream()
                .map(tuple -> new AppointmentStatusCount(
                        tuple.get("status", AppointmentStatus.class).toString(),
                        tuple.get("count", Integer.class)
                ))
                .toList();
    }

    public List<AppointmentEntity> filterAppointment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return List.of();
    }

}
