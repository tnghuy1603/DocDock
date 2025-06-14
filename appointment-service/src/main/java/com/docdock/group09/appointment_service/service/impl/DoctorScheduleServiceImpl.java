package com.docdock.group09.appointment_service.service.impl;

import com.docdock.group09.appointment_service.dto.response.DoctorScheduleResponse;
import com.docdock.group09.appointment_service.entity.DoctorScheduleEntity;
import com.docdock.group09.appointment_service.repository.DoctorScheduleRepository;
import com.docdock.group09.appointment_service.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    @Override
    public void bulkInsertData() {
        List<DoctorScheduleEntity> schedules = new ArrayList<>();
        List<String> doctorIds = List.of("doc-001", "doc-002", "doc-003");

        for (int day = 0; day < 14; day++) {
            LocalDateTime start = LocalDateTime.now().plusDays(day).withHour(8).withMinute(0);
            LocalDateTime end = start.plusHours(4);

            for (String doctorId : doctorIds) {
                DoctorScheduleEntity schedule = new DoctorScheduleEntity();
                schedule.setId(UUID.randomUUID().toString());
                schedule.setDoctorId(doctorId);
                schedule.setStartTime(start);
                schedule.setEndTime(end);
                schedules.add(schedule);
            }
        }
        doctorScheduleRepository.saveAll(schedules);

    }

    @Override
    public List<DoctorScheduleResponse> getDoctorScheduleIn7days(String doctorId, LocalDate currentDate) {
        LocalDateTime startOfCurrentDate = currentDate.atStartOfDay();
        LocalDateTime endOfNext7Days = startOfCurrentDate.plusDays(7);
        List<DoctorScheduleEntity> scheduleEntities = doctorScheduleRepository.findSchedulesForNext7Days(doctorId, startOfCurrentDate, endOfNext7Days);
        Map<LocalDate, DoctorScheduleResponse> scheduleMap = new HashMap<>();
        for (DoctorScheduleEntity scheduleEntity : scheduleEntities) {
            LocalDate date = scheduleEntity.getStartTime().toLocalDate();
            DoctorScheduleResponse scheduleResponse = scheduleMap.getOrDefault(date, new DoctorScheduleResponse());
            scheduleResponse.setDoctorId(scheduleEntity.getDoctorId());
            scheduleResponse.setDate(date);
            DoctorScheduleResponse.WorkShift workShift = new DoctorScheduleResponse.WorkShift();
            workShift.setStartTime(scheduleEntity.getStartTime());
            workShift.setEndTime(scheduleEntity.getEndTime());
            if (scheduleResponse.getWorkShifts() == null) {
                scheduleResponse.setWorkShifts(new ArrayList<>());
            }
            scheduleResponse.getWorkShifts().add(workShift);
            scheduleMap.put(date, scheduleResponse);
        }
        return new ArrayList<>(scheduleMap.values());
    }

}
