package com.medical.dossier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MedecinDTO {
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private String specialite;
    private Long serviceId;
    private Long utilisateurId;

    // Objet imbriqué pour l'affichage frontend (service.nomService)
    private ServiceInfo service;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceInfo {
        private Long id;
        private String nomService;
    }
}
