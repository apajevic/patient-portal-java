package com.patientportal.dto;

import com.patientportal.model.Gender;

import java.util.Set;

public record UpdateUserDTO(
        String name,
        String phone,
        String address,
        Gender gender,
        Set<Long> conditions
) {
}
