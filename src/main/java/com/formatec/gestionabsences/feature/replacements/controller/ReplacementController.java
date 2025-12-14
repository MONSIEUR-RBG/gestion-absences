// feature/remplacement/controller/RemplacementController.java
package com.formatec.gestionabsences.feature.replacements.controller;

import com.formatec.gestionabsences.feature.replacements.dto.*;
import com.formatec.gestionabsences.feature.replacements.service.ReplacementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remplacements")
@RequiredArgsConstructor
public class ReplacementController {

    private final ReplacementService service;

    @PostMapping
    public ResponseEntity<ReplacementResponse> planifier(@RequestBody ReplacementRequest req) throws Throwable {
        return ResponseEntity.ok(service.planifier(req));
    }

    @GetMapping
    public List<ReplacementResponse> getPlanning() {
        return service.getPlanning();
    }

    @GetMapping("/mes-remplacements")
    public List<ReplacementResponse> getMesRemplacements() {
        return service.getMesRemplacements();
    }
}