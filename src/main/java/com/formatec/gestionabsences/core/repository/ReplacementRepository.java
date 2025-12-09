package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Remplacement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RemplacementRepository extends JpaRepository<Remplacement, Long> {
    List<Remplacement> findByEnseignantAbsentId(Long enseignantId);
    List<Remplacement> findByEnseignantRemplacantId(Long enseignantId);
    List<Remplacement> findByDateDebutBetween(LocalDate debut, LocalDate fin);
}