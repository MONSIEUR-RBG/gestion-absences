package com.formatec.gestionabsences.feature.auth.dto;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID userId,
        String firstName,
        String lastName,
        String role
) {
}