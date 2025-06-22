package com.docdock.group09.notification_service.utils;

import lombok.experimental.UtilityClass;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@UtilityClass
public class SmsTemplate {
    public static String getAppointmentReminder(String doctorName, LocalDateTime startTime, LocalDateTime endTime) {
        return MessageFormat.format(
                "Reminder: You have an appointment with Dr. {0} from {1} to {2}",
                doctorName, startTime, endTime
        );
    }
    public static String getAppointmentConfirmation(String doctorName, LocalDateTime startTime, LocalDateTime endTime) {
        return MessageFormat.format("Your appointment with Dr {0} from {1} to {2} have been confirmed", doctorName, startTime, endTime);
    }

    public static String getAppointmentCancelled(String doctorName, LocalDateTime startTime, LocalDateTime endTime, String cancelReason) {
        return MessageFormat.format(
                "Your appointment with Dr. {0} on {1} from {2} to {3} has been cancelled. Reason: {4}. Please contact us to reschedule.",
                doctorName,
                startTime.toLocalDate(),
                startTime.toLocalTime(),
                endTime.toLocalTime(),
                cancelReason
        );
    }

    public static String getPrescriptionReady(String doctorName, String pharmacyName, String pickupDeadlineDate) {
        return String.format(
                "Your prescription from Dr. %s is ready for pickup at %s. Please collect it by %s.",
                doctorName, pharmacyName, pickupDeadlineDate
        );
    }
}
