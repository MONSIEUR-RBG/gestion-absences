package com.formatec.gestionabsences.feature.requests.DTO;

public record FichierResponse(
        Long id,
        String nomOriginal,
        String chemin,
        String typeMime,
        Long taille)
{}
