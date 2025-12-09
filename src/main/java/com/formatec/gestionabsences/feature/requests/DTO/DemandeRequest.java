package com.formatec.gestionabsences.feature.requests.DTO;

import com.formatec.gestionabsences.core.entity.TypeDemande;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

public record DemandeRequest(
        @NotNull TypeDemande typeDemande,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        LocalDate dateReprisePrevue,
        String motif,
        List<MultipartFile> fichiers // optionnel
) {}
