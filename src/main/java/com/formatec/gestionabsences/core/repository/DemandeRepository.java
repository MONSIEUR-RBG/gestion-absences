package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Demande;
import com.formatec.gestionabsences.core.entity.Departement;
import com.formatec.gestionabsences.core.entity.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByDemandeurId(UUID demandeurId);
    List<Demande> findByDepartementIdAndStatut(Long departementId, StatutDemande statut);
    List<Demande> findByValideurId(UUID valideurId);
    List<Demande> findByStatut(StatutDemande statut);
    List<Demande> findByDateRetourEffectifIsNullAndDateReprisePrevueBefore(LocalDate date);
    List<Demande> findByDemandeurEmail(String email);
    List<Demande> findByDepartementAndStatut(Departement departement, StatutDemande statutDemande);
}