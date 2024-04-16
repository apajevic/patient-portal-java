package com.patientportal.dto;

import java.time.LocalDate;

public record UpdatePrescriptionDTO(LocalDate validUntil, String notes, String status) {}
