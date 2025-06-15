package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.entity.DoctorScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorScheduleEntity, String> {
    @Query("SELECT s FROM DoctorScheduleEntity s " +
            "WHERE s.doctorId = :doctorId " +
            "AND s.startTime BETWEEN :startDate AND :endDate " +
            "ORDER BY s.startTime ASC")
    List<DoctorScheduleEntity> findSchedulesForNext7Days(@Param("doctorId") String doctorId,
                                                         @Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate);

    List<DoctorScheduleEntity> findByDoctorIdAndStartTimeBetween(String doctorId, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}
