package com.formatec.gestionabsences.feature.requests.service;

import com.formatec.gestionabsences.core.entity.*;
import com.formatec.gestionabsences.core.repository.*;
import com.formatec.gestionabsences.feature.requests.DTO.*;
import lombok.RequiredArgsConstructor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DemandeService {

    private final DemandeRepository demandeRepo;
    private final UserRepository userRepo;
    private final FichierJointRepository fichierRepo;
    private final HistoriqueActionRepository histRepo;

    @Value("${app.upload.dir:${user.home}/uploads}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        try {
            uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public DemandeResponse soumettre(DemandeRequest req, List<MultipartFile> fichiers) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity demandeur = userRepo.findByEmail(email).orElseThrow();

        Demande demande = Demande.builder()
                .typeDemande(req.typeDemande())
                .statut(StatutDemande.SOUMISE)
                .dateDebut(req.dateDebut())
                .dateFin(req.dateFin())
                .dateReprisePrevue(req.dateReprisePrevue())
                .motif(req.motif())
                .demandeur(demandeur)
                .departement(demandeur.getDepartement())
                .dateCreation(LocalDateTime.now())
                .build();

        demandeRepo.save(demande);

        // Historique création
        histRepo.save(HistoriqueAction.builder()
                .demande(demande)
                .utilisateur(demandeur)
                .action(ActionHistorique.CREATION)
                .dateAction(LocalDateTime.now())
                .build());

        // Upload fichiers (optionnel)
        if (fichiers != null && !fichiers.isEmpty()) {
            fichiers.forEach(f -> sauvegarderFichier(f, demande));
        }

        // TODO : Envoyer notification au chef de département

        return toResponse(demande);
    }

    public DemandeResponse valider(Long id) {
        return changerStatut(id, StatutDemande.VALIDEE, ActionHistorique.VALIDATION);
    }

    public DemandeResponse rejeter(Long id, String motif) {
        return changerStatut(id, StatutDemande.REJETEE, ActionHistorique.REJET, motif);
    }

    private DemandeResponse changerStatut(Long id, StatutDemande statut, ActionHistorique action) {
        return changerStatut(id, statut, action, null);
    }

    private DemandeResponse changerStatut(Long id, StatutDemande statut, ActionHistorique action, String commentaire) {
        Demande d = demandeRepo.findById(id).orElseThrow();
        d.setStatut(statut);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity valideur = userRepo.findByEmail(email).orElseThrow();
        d.setValideur(valideur);
        demandeRepo.save(d);

        HistoriqueAction.Builder histBuilder = HistoriqueAction.builder()
                .demande(d)
                .utilisateur(valideur)
                .action(action)
                .dateAction(LocalDateTime.now());

        if (commentaire != null && !commentaire.isEmpty()) {
            histBuilder.commentaire(commentaire);
        }

        histRepo.save(histBuilder.build());

        return toResponse(d);
    }

    private void sauvegarderFichier(MultipartFile file, Demande demande) {
        try {
            String nom = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = uploadPath.resolve(nom);
            Files.copy(file.getInputStream(), path);

            fichierRepo.save(FichierJoint.builder()
                    .demande(demande)
                    .nomOriginal(file.getOriginalFilename())
                    .cheminStockage(path.toString())
                    .typeMime(file.getContentType())
                    .taille(file.getSize())
                    .build());
        } catch (IOException e) {
            throw new RuntimeException("Erreur upload", e);
        }
    }

    private DemandeResponse toResponse(Demande d) {
        // Mapping simplifié (tu peux faire un MapStruct plus tard)
        String demandeur = d.getDemandeur().getFirstName() + " " + d.getDemandeur().getLastName();
        String valideur = d.getValideur() != null ? d.getValideur().getFirstName() + " " + d.getValideur().getLastName() : null;
        List<FichierResponse> fichiers = d.getFichiers() != null ? d.getFichiers().stream()
                .map(f -> new FichierResponse(f.getId(), f.getNomOriginal(), f.getCheminStockage(), f.getTypeMime(), f.getTaille()))
                .toList() : Collections.emptyList();

        return new DemandeResponse(d.getId(), d.getTypeDemande(), d.getStatut(), d.getDateDebut(),
                d.getDateFin(), d.getDateReprisePrevue(), d.getDateRetourEffectif(), d.getMotif(),
                demandeur, valideur, d.getDateCreation(), fichiers);
    }

    public List<DemandeResponse> mesDemandes() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return demandeRepo.findByDemandeurEmail(email).stream().map(this::toResponse).toList();
    }

    public List<DemandeResponse> aValider() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity chef = userRepo.findByEmail(email).orElseThrow();
        return demandeRepo.findByDepartementAndStatut(chef.getDepartement(), StatutDemande.SOUMISE)
                .stream().map(this::toResponse).toList();
    }
}