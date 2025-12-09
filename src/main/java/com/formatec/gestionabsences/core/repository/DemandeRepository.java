package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Demande;
import com.formatec.gestionabsences.core.entity.Departement;
import com.formatec.gestionabsences.core.entity.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByDemandeurId(Long demandeurId);
    List<Demande> findByDepartementIdAndStatut(Long departementId, StatutDemande statut);
    List<Demande> findByValideurId(Long valideurId);
    List<Demande> findByStatut(StatutDemande statut);
    List<Demande> findByDateRetourEffectifIsNullAndDateReprisePrevueBefore(LocalDate date);

    Collection<Object> findByDepartementAndStatut(Departement departement, StatutDemande statutDemande);

    Collection<Object> findByDemandeurEmail(String email);
}