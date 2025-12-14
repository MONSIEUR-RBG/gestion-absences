// feature/remplacement/service/RemplacementService.java
package com.formatec.gestionabsences.feature.replacements.service;

import com.formatec.gestionabsences.core.entity.*;
import com.formatec.gestionabsences.core.repository.*;
import com.formatec.gestionabsences.feature.replacements.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplacementService {

    private final ReplacementRepository remplacementRepo;
    private final UserRepository userRepo;
    private final DemandeRepository demandeRepo;

    public ReplacementResponse planifier(ReplacementRequest req) throws Throwable {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity responsable = userRepo.findByEmail(email).orElseThrow();

        UserEntity absent = userRepo.findById(req.enseignantAbsentId())
                .orElseThrow(() -> new RuntimeException("Enseignant absent non trouvé"));

        UserEntity remplaçant = userRepo.findById(req.enseignantRemplacantId())
                .orElseThrow(() -> new RuntimeException("Remplaçant non trouvé"));

        Demande demande = req.demandeId() != null
                ? demandeRepo.findById(req.demandeId()).orElse(null)
                : null;

        Remplacement remplacement = Remplacement.builder()
                .enseignantAbsent(absent)
                .enseignantRemplacant(remplaçant)
                .demande(demande)
                .coursOuMatiere(req.coursOuMatiere())
                .dateDebut(req.dateDebut())
                .dateFin(req.dateFin())
                .statut(StatutRemplacement.PLANIFIE)
                .dateCreation(LocalDateTime.now())
                .build();

        remplacementRepo.save(remplacement);

        // TODO : Envoyer notification aux deux enseignants (semaine suivante)

        return toResponse(remplacement);
    }

    public List<ReplacementResponse> getPlanning() {
        return remplacementRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReplacementResponse> getMesRemplacements() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepo.findByEmail(email).orElseThrow();

        return remplacementRepo.findByEnseignantRemplacantId(user.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    private ReplacementResponse toResponse(Remplacement r) {
        String absent = r.getEnseignantAbsent().getNom() + " " + r.getEnseignantAbsent().getPrenom();
        String remplaçant = r.getEnseignantRemplacant().getNom() + " " + r.getEnseignantRemplacant().getPrenom();

        return new ReplacementResponse(
                r.getId(),
                absent,
                remplaçant,
                r.getCoursOuMatiere(),
                r.getDateDebut(),
                r.getDateFin(),
                r.getStatut().name(),
                r.getDateCreation()
        );
    }
}