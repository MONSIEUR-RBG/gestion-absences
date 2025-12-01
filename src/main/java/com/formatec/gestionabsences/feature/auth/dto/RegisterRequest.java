package com.formatec.gestionabsences.feature.auth.dto;

import com.formatec.gestionabsences.core.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String confirmPassword,
        Role role
) {
}