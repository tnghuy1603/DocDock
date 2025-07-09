package com.docdock.group09.appointment_service.service.impl;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.response.AvailableScheduleResponse;
import com.docdock.group09.appointment_service.dto.response.DoctorScheduleResponse;
import com.docdock.group09.appointment_service.dto.response.UserInfo;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import com.docdock.group09.appointment_service.entity.DoctorScheduleEntity;
import com.docdock.group09.appointment_service.exception.AppointmentServiceException;
import com.docdock.group09.appointment_service.repository.AppointmentRepository;
import com.docdock.group09.appointment_service.repository.DoctorScheduleRepository;
import com.docdock.group09.appointment_service.service.DoctorScheduleService;
import com.docdock.group09.appointment_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserServiceClient userServiceClient;
    private final AppointmentRepository appointmentRepository;
    @Override
    public void bulkInsertData() {
        List<DoctorScheduleEntity> schedules = new ArrayList<>();
        List<String> doctorIds = List.of("cd90c404-6e72-4d57-8d97-05add77c7be1");

        for (int day = 0; day < 14; day++) {
            LocalDateTime start = LocalDateTime.now().plusDays(day).withHour(8).withMinute(0);
            LocalDateTime end = start.plusHours(4);

            for (String doctorId : doctorIds) {
                DoctorScheduleEntity schedule = new DoctorScheduleEntity();
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

    @Override
    public AvailableScheduleResponse getTodayAvailable(String doctorId, LocalDate date) {
        UserInfo doctorInfo = userServiceClient.getUserDetails(doctorId, "DOCTOR").getData();
        if (doctorInfo == null) {
            throw AppointmentServiceException.buildBadRequest("Not found any doctor with that id");
        }
        LocalDateTime atStartOfTheDay = date.atStartOfDay();
        LocalDateTime atEndOfTheDay = date.atTime(LocalTime.MAX);
        List<DoctorScheduleEntity> todaySchedules = doctorScheduleRepository.findByDoctorIdAndStartTimeBetween(doctorId, atStartOfTheDay, atEndOfTheDay);
        if (todaySchedules.isEmpty()) {
            return AvailableScheduleResponse.builder()
                    .doctorId(doctorId)
                    .timeFrames(Collections.emptyList())
                    .build();
        }
        List<AppointmentEntity> todayAppointment = appointmentRepository.findByDoctorIdAndStartTimeBetweenAndStatusIsNot(doctorId, atStartOfTheDay, atEndOfTheDay, AppointmentStatus.CANCELLED);
        List<AvailableScheduleResponse.TimeFrame> availableTimeFrame = new ArrayList<>();
        for (DoctorScheduleEntity schedule : todaySchedules) {
            LocalDateTime scheduleStart = schedule.getStartTime();
            LocalDateTime scheduleEnd = schedule.getEndTime();
            LocalDateTime pointer = scheduleStart;

            for (AppointmentEntity appointment : todayAppointment) {
                if (pointer.isBefore(appointment.getStartTime())) {
                    availableTimeFrame.add(new AvailableScheduleResponse.TimeFrame(pointer, appointment.getStartTime()));
                }
                pointer = appointment.getEndTime().isAfter(pointer) ? appointment.getEndTime() : pointer;
            }

            if (pointer.isBefore(scheduleEnd)) {
                availableTimeFrame.add(new AvailableScheduleResponse.TimeFrame(pointer, scheduleEnd));
            }
        }
        return AvailableScheduleResponse.builder()
                .doctorId(doctorId)
                .timeFrames(availableTimeFrame)
                .build();
    }


}
