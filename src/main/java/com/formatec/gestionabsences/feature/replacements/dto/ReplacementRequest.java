// feature/remplacement/dto/RemplacementRequest.java
package com.formatec.gestionabsences.feature.replacements.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record ReplacementRequest(
        @NotNull UUID enseignantAbsentId,
        @NotNull UUID enseignantRemplacantId,
        Long demandeId, // optionnel
        String coursOuMatiere,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin
) {}