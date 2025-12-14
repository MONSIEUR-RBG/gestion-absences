// feature/retour/scheduler/RetardScheduler.java
package com.formatec.gestionabsences.feature.retours.scheduler;

import com.formatec.gestionabsences.core.entity.*;
import com.formatec.gestionabsences.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RetardScheduler {

    private final DemandeRepository demandeRepo;
    private final NotificationRepository notifRepo;
    private final UserRepository userRepo;

    @Scheduled(cron = "0 0 0 * * ?") // Tous les jours à minuit
    public void detecterRetards() {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate limite = aujourdHui.minusDays(3); // alerte après 3 jours de retard

        List<Demande> demandesRetard = demandeRepo.findByDateRetourEffectifIsNullAndDateReprisePrevueBefore(limite);

        for (Demande d : demandesRetard) {
            // Notification au RH
            userRepo.findByRole(Role.RH).forEach(rh -> {
                notifRepo.save(Notification.builder()
                        .destinataire(rh)
                        .demande(d)
                        .type(TypeNotification.ALERTE_RETARD)
                        .titre("ALERTE RETARD DE RETOUR")
                        .message(d.getDemandeur().getNom() + " " + d.getDemandeur().getPrenom() +
                                " n'a pas marqué son retour (reprise prévue le " + d.getDateReprisePrevue() + ")")
                        .dateEnvoi(LocalDateTime.now())
                        .build());
            });

            // Notification au chef de département
            UserEntity chef = d.getDepartement().getDoyen();
            if (chef != null) {
                notifRepo.save(Notification.builder()
                        .destinataire(chef)
                        .demande(d)
                        .type(TypeNotification.ALERTE_RETARD)
                        .titre("Retard de retour dans votre département")
                        .message("Un membre de votre département est en retard de retour")
                        .dateEnvoi(LocalDateTime.now())
                        .build());
            }
        }
    }
}