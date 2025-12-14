// feature/retour/dto/RetourRequest.java
package com.formatec.gestionabsences.feature.retours.dto;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public record RetourRequest(
        Long demandeId,
        LocalDate dateRetourEffectif,
        MultipartFile fichierJustificatif // optionnel (photo billet, certificat, etc.)
) {}