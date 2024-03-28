package com.patientportal.dto;

import com.patientportal.model.Gender;

public record UpdateUserDTO(
        String name,
        String phone,
        String address,
        Gender gender
) {
}
