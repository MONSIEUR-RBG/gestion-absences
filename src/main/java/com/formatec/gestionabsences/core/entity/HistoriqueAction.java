package com.formatec.gestionabsences.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_actions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriqueAction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Demande demande;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity utilisateur;

    @Enumerated(EnumType.STRING)
    private ActionHistorique action;

    private String commentaire;
    private LocalDateTime dateAction = LocalDateTime.now();
}
