package com.formatec.gestionabsences.core.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    private UserEntity destinataire;

    @ManyToOne(fetch = FetchType.LAZY)
    private Demande demande; // peut être null

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    private String titre;
    private String message;

    private boolean lue = false;
    private LocalDateTime dateEnvoi = LocalDateTime.now();
}

