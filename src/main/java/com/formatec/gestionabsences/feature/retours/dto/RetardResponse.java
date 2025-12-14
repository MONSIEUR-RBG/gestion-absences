package com.formatec.gestionabsences.feature.retours.dto;

import java.time.LocalDate;

public record RetardResponse(
        Long demandeId,
        String demandeurNomComplet,
        LocalDate dateReprisePrevue,
        int joursRetard,
        String statut
) {}