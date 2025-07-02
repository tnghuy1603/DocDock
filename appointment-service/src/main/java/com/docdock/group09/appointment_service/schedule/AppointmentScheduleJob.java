package com.docdock.group09.appointment_service.schedule;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import com.docdock.group09.appointment_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppointmentScheduleJob {
    private final AppointmentRepository appointmentRepository;

    @Scheduled(initialDelay = 2000)
    public void updateExpiredAppointment() {
        List<AppointmentEntity> expiredAppointments = appointmentRepository.findByStatusIsAndStartTimeAfter(AppointmentStatus.PENDING, LocalDateTime.now());
        for (AppointmentEntity expiredAppointment : expiredAppointments) {
            expiredAppointment.setStatus(AppointmentStatus.CANCELLED);
            expiredAppointment.setCancelReason("Expired appointment");
            expiredAppointment.setUpdatedAt(LocalDateTime.now());
        }
        appointmentRepository.saveAll(expiredAppointments);
    }
    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void remindUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);
        LocalDateTime twoHourLater = now.plusHours(2);
        List<AppointmentEntity> appointmentEntities = appointmentRepository.findByStatusIsAndStartTimeBetween(AppointmentStatus.CONFIRMED, oneHourLater, twoHourLater);
        LocalDateTime oneDateLaterStart = now.plusDays(1);
        LocalDateTime oneDateLaterEnd = oneDateLaterStart.plusDays(2);
        List<AppointmentEntity> tomorrowAppointments = appointmentRepository.findByStatusIsAndStartTimeBetween(AppointmentStatus.CONFIRMED, oneDateLaterStart, oneDateLaterEnd);
        //TODO send notifications

    }
}
