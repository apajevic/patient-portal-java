package com.patientportal.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

public record AppointmentDTO(
        @NotBlank Long patientId,
        @NotBlank Long doctorId,
        @NotBlank @Future Date date,
        @NotBlank String startTime,
        @NotBlank String endTime,
        @NotBlank String location,
        @NotBlank String description,
        Long prescription
) {
}

