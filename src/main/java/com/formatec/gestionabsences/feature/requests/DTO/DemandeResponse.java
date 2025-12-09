package com.formatec.gestionabsences.feature.requests.DTO;

import com.formatec.gestionabsences.core.entity.StatutDemande;
import com.formatec.gestionabsences.core.entity.TypeDemande;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DemandeResponse(
        Long id,
        TypeDemande typeDemande,
        StatutDemande statut,
        LocalDate dateDebut,
        LocalDate dateFin,
        LocalDate dateReprisePrevue,
        LocalDate dateRetourEffectif,
        String motif,
        String demandeurNomComplet,
        String valideurNomComplet,
        LocalDateTime dateCreation,
        List<FichierResponse> fichiers) {}

