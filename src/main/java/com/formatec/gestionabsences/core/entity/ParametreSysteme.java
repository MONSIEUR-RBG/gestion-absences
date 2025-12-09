package com.formatec.gestionabsences.core.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parametres_systeme")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametreSysteme {

    @Id
    private String cle; // ex: "delai_retard_jours"

    @Column(nullable = false)
    private String valeur;

    private String description;
}