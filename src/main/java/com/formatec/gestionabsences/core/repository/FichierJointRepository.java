package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.FichierJoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FichierJointRepository extends JpaRepository<FichierJoint, Long> {
    List<FichierJoint> findByDemandeId(Long demandeId);
}