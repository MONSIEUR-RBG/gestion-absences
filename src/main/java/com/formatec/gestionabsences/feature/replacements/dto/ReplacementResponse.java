// feature/remplacement/dto/RemplacementResponse.java
package com.formatec.gestionabsences.feature.replacements.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReplacementResponse(
        Long id,
        String enseignantAbsentNomComplet,
        String enseignantRemplacantNomComplet,
        String coursOuMatiere,
        LocalDate dateDebut,
        LocalDate dateFin,
        String statut,
        LocalDateTime dateCreation
) {}