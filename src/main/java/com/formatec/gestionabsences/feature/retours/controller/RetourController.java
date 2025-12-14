package com.formatec.gestionabsences.feature.retours.controller;

import com.formatec.gestionabsences.feature.retours.dto.RetardResponse;
import com.formatec.gestionabsences.feature.retours.dto.RetourRequest;
import com.formatec.gestionabsences.feature.retours.service.RetourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// feature/retour/controller/RetourController.java
@RestController
@RequestMapping("/api/retours")
@RequiredArgsConstructor
public class RetourController {

    private final RetourService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> enregistrerRetour(
            @RequestPart RetourRequest req,
            @RequestPart(required = false) MultipartFile fichier) {
        service.enregistrerRetour(req, fichier);
        return ResponseEntity.ok("Retour enregistré avec succès");
    }

    @GetMapping("/retards")
    public List<RetardResponse> getRetards() {
        return service.getRetards();
    }
}