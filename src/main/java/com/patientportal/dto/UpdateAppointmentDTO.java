package com.patientportal.dto;


import java.util.Date;

public record UpdateAppointmentDTO(
        Date date,
        String startTime,
        String endTime,
        String location,
        String status,
        String conclusion,
        String description
) {
}
