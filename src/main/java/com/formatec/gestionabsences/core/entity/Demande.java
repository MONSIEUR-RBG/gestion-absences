package com.formatec.gestionabsences.core.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "demandes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Demande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.SOUMISE;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateReprisePrevue;
    private LocalDate dateRetourEffectif;

    private String motif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id")
    private UserEntity demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "valideur_id")
    private UserEntity valideur;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departement departement;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichierJoint> fichiers = new ArrayList<>();

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private List<HistoriqueAction> historique = new ArrayList<>();
}
