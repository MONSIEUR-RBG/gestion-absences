package com.formatec.gestionabsences.core.repository;
import com.formatec.gestionabsences.core.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByDemandeIdOrderByDateCommentaireDesc(Long demandeId);
}
