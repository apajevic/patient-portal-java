package com.patientportal.dto;

import com.patientportal.model.Gender;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

public record RegisterDTO(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank Gender gender,
        @NotBlank String password,
        @NotBlank String passwordConfirm,
        Set<UUID> conditions
) {
}
