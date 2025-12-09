package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.HistoriqueAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoriqueActionRepository extends JpaRepository<HistoriqueAction, Long> {
    List<HistoriqueAction> findByDemandeIdOrderByDateActionDesc(Long demandeId);
}
