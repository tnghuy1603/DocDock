package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentStatusCount;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        if (request.getDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("date"), request.getDate()));
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

    public Page<AppointmentEntity> filterAppointment(FilterAppointmentRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppointmentEntity> cq = cb.createQuery(AppointmentEntity.class);
        Root<AppointmentEntity> root = cq.from(AppointmentEntity.class);
        List<Predicate> andPredicate = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(request.getDoctorId())) {
            andPredicate.add(cb.equal(root.get("doctorId"), request.getDoctorId()));
        }

        if (StringUtils.isNotEmpty(request.getPatientId())) {
            andPredicate.add(cb.equal(root.get("patientId"), request.getPatientId()));
        }

        if (request.getStatus() != null) {
            andPredicate.add(cb.equal(root.get("status"), request.getStatus()));
        }

        if (request.getType() != null) {
            andPredicate.add(cb.equal(root.get("type"), request.getType()));
        }

        if (StringUtils.isNotEmpty(request.getReason())) {
            orPredicates.add(cb.like(root.get("reason"), "%" + request.getReason() + "%"));
        }

        if (StringUtils.isNotEmpty(request.getPatientName())) {
            orPredicates.add(cb.like(root.get("patientName"), "%" + request.getPatientName() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getAppointmentId())) {
            orPredicates.add(cb.like(root.get("id"), "%" + request.getAppointmentId() + "%"));
        }

        if (StringUtils.isNotEmpty(request.getDoctorName())) {
            orPredicates.add(cb.like(root.get("doctorName"), request.getDoctorName()));
        }
        Predicate orPredicate = null;
        if (!orPredicates.isEmpty()) {
            orPredicate = cb.or(orPredicates.toArray(new Predicate[0]));
            andPredicate.add(orPredicate);
        }

        if (!andPredicate.isEmpty()) {
            cq.where(andPredicate.toArray(new Predicate[0]));
        }


        cq.orderBy(cb.asc(root.get("createdAt")), cb.asc(root.get("updatedDate")));
        TypedQuery<AppointmentEntity> query = entityManager.createQuery(cq);
        query.setFirstResult(request.getOffset()).setMaxResults(request.getLimit());

        List<AppointmentEntity> pageContent = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<AppointmentEntity> rootCount = countQuery.from(AppointmentEntity.class);
        countQuery.select(cb.count(rootCount));
        List<Predicate> countAndPredicates = new ArrayList<>(andPredicate); // reuse same predicates for count
        if (!countAndPredicates.isEmpty()) {
            countQuery.where(countAndPredicates.toArray(new Predicate[0]));
        }

        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(pageContent, PageRequest.of(request.getOffset(), request.getLimit()), totalElements);
    }

}
