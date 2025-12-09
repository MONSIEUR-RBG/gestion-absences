package com.formatec.gestionabsences.core.entity;

import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fichiers_joint")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FichierJoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id")
    private Demande demande;

    private String nomOriginal;
    private String cheminStockage;
    private String typeMime;
    private Long taille;

    private LocalDateTime dateUpload = LocalDateTime.now();
}