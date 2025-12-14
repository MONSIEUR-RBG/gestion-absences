// feature/remplacement/dto/RemplacementRequest.java
package com.formatec.gestionabsences.feature.replacements.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReplacementRequest(
        @NotNull Long enseignantAbsentId,
        @NotNull Long enseignantRemplacantId,
        Long demandeId, // optionnel
        String coursOuMatiere,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin
) {}