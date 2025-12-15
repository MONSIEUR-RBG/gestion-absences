package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDestinataireIdAndLueFalseOrderByDateEnvoiDesc(UUID userId);
    long countByDestinataireIdAndLueFalse(UUID userId);
}