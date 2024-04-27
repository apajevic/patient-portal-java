package com.patientportal.dto;

import com.patientportal.model.Gender;

import java.util.Set;
import java.util.UUID;

public record UpdateUserDTO(
        String name,
        String phone,
        String address,
        Gender gender,
        Set<UUID> conditions
) {
}
