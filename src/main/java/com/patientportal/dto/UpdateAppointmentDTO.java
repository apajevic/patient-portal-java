package com.patientportal.dto;


import java.util.Date;
import java.util.UUID;

public record UpdateAppointmentDTO(
        Date date,
        String startTime,
        String endTime,
        String location,
        String status,
        String conclusion,
        String description,
        UUID prescription
) {
}
