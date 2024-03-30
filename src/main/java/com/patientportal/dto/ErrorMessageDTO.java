package com.patientportal.dto;

public record ErrorMessageDTO(
        int status,
       String message) {
}
