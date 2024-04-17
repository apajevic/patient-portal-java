package com.patientportal.dto;

import com.patientportal.model.Gender;
import jakarta.validation.constraints.*;

import java.util.Set;

public record RegisterDTO(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank Gender gender,
        @NotBlank String password,
        @NotBlank String passwordConfirm,
        Set<Long> conditions
) {
}
