package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Remplacement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ReplacementRepository  extends JpaRepository<Remplacement, Long>{
    List<Remplacement> findByEnseignantRemplacantId(UUID id);

    List<Remplacement> findAll();

    public interface RemplacementRepository extends JpaRepository<Remplacement, Long> {
        List<Remplacement> findByEnseignantAbsentId(Long enseignantId);
        List<Remplacement> findByEnseignantRemplacantId(Long enseignantId);
        List<Remplacement> findByDateDebutBetween(LocalDate debut, LocalDate fin);
    }
}
