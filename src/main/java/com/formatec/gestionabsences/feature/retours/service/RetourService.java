// feature/retour/service/RetourService.java
package com.formatec.gestionabsences.feature.retours.service;

import com.formatec.gestionabsences.core.entity.*;
import com.formatec.gestionabsences.core.repository.*;
import com.formatec.gestionabsences.feature.retours.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RetourService {

    private final DemandeRepository demandeRepo;
    private final UserRepository userRepo;
    private final FichierJointRepository fichierRepo;
    private final HistoriqueActionRepository histRepo;
    private final NotificationRepository notifRepo;

    private final Path uploadDir = Paths.get("uploads/retours").toAbsolutePath();

    public void enregistrerRetour(RetourRequest req, MultipartFile fichier) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity acteur = userRepo.findByEmail(email).orElseThrow();

        Demande demande = demandeRepo.findById(req.demandeId())
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // Vérification : la demande doit être VALIDEE
        if (demande.getStatut() != StatutDemande.VALIDEE) {
            throw new RuntimeException("Impossible d'enregistrer le retour sur une demande non validée");
        }

        demande.setDateRetourEffectif(req.dateRetourEffectif() != null ? req.dateRetourEffectif() : LocalDate.now());
        demande.setStatut(StatutDemande.TERMINEE);
        demandeRepo.save(demande);

        // Historique
        histRepo.save(HistoriqueAction.builder()
                .demande(demande)
                .utilisateur(acteur)
                .action(ActionHistorique.RETOUR)
                .commentaire("Retour enregistré le " + LocalDate.now())
                .dateAction(LocalDateTime.now())
                .build());

        // Upload justificatif si présent
        if (fichier != null && !fichier.isEmpty()) {
            try {
                String nom = UUID.randomUUID() + "_" + fichier.getOriginalFilename();
                Path path = uploadDir.resolve(nom);
                Files.copy(fichier.getInputStream(), path);

                fichierRepo.save(FichierJoint.builder()
                        .demande(demande)
                        .nomOriginal(fichier.getOriginalFilename())
                        .cheminStockage(path.toString())
                        .typeMime(fichier.getContentType())
                        .taille(fichier.getSize())
                        .dateUpload(LocalDateTime.now())
                        .build());
            } catch (Exception e) {
                throw new RuntimeException("Erreur upload justificatif retour", e);
            }
        }

        // Notification RH
        UserEntity rh = userRepo.findByRole(Role.RH).stream().findFirst().orElse(null);
        if (rh != null) {
            notifRepo.save(Notification.builder()
                    .destinataire(rh)
                    .demande(demande)
                    .type(TypeNotification.RETOUR_ENREGISTRE)
                    .titre("Retour enregistré")
                    .message(acteur.getNom() + " " + acteur.getPrenom() + " a marqué son retour pour la demande n°" + demande.getId())
                    .dateEnvoi(LocalDateTime.now())
                    .build());
        }
    }

    // Liste des retards (pour RH)
    public List<RetardResponse> getRetards() {
        LocalDate aujourdHui = LocalDate.now();
        return demandeRepo.findByDateRetourEffectifIsNullAndDateReprisePrevueBefore(aujourdHui.minusDays(1)).stream()
                .map(d -> {
                    long joursRetard = ChronoUnit.DAYS.between(d.getDateReprisePrevue(), aujourdHui);
                    String demandeur = d.getDemandeur().getNom() + " " + d.getDemandeur().getPrenom();
                    return new RetardResponse(d.getId(), demandeur, d.getDateReprisePrevue(), (int) joursRetard, "EN_RETARD");
                })
                .toList();
    }
}