package com.formatec.gestionabsences.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "remplacements")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Remplacement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_absent_id", nullable = false)
    private UserEntity enseignantAbsent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_remplacant_id", nullable = false)
    private UserEntity enseignantRemplacant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Demande demande; // lien optionnel avec la demande d’absence

    private String coursOuMatiere;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutRemplacement statut = StatutRemplacement.PLANIFIE;

    private LocalDateTime dateCreation = LocalDateTime.now();
}

