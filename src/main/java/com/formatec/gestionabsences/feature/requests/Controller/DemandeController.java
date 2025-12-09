package com.formatec.gestionabsences.feature.requests.Controller;

import com.formatec.gestionabsences.feature.requests.DTO.DemandeRequest;
import com.formatec.gestionabsences.feature.requests.DTO.DemandeResponse;
import com.formatec.gestionabsences.feature.requests.service.DemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
public class DemandeController {

    private final DemandeService service;

    @PostMapping(consumes = "multipart/form-data")
    public DemandeResponse creer(@RequestPart DemandeRequest req,
                                 @RequestPart(required = false) List<MultipartFile> fichiers) {
        return service.soumettre(req, fichiers);
    }

    @GetMapping("/mes-demandes")
    public List<DemandeResponse> mesDemandes() {
        return service.mesDemandes();
    }

    @GetMapping("/a-valider")
    public List<DemandeResponse> aValider() {
        return service.aValider();
    }

    @PatchMapping("/{id}/valider")
    public DemandeResponse valider(@PathVariable Long id) {
        return service.valider(id);
    }

    @PatchMapping("/{id}/rejeter")
    public DemandeResponse rejeter(@PathVariable Long id, @RequestBody String motif) {
        return service.rejeter(id, motif);
    }
}