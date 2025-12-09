package com.formatec.gestionabsences.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    @OneToOne
    @JoinColumn(name = "doyen_id")
    private UserEntity doyen;

    @OneToMany(mappedBy = "departement")
    private List<UserEntity> membres = new ArrayList<>();
}

