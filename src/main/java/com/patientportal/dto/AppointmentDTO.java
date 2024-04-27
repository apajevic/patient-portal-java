package com.patientportal.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public record AppointmentDTO(
        @NotBlank UUID patientId,
        @NotBlank UUID doctorId,
        @NotBlank @Future Date date,
        @NotBlank String startTime,
        @NotBlank String endTime,
        @NotBlank String location,
        @NotBlank String description,
        UUID prescription
) {
}

