package com.medical.dossier.dto;

import com.medical.dossier.entity.StatutRdv;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class RendezVousDTO {
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long medecinId;

    @NotNull
    private LocalDateTime dateRdv;

    private StatutRdv statut;
    private String motif;

    // Objets imbriqués pour l'affichage frontend
    private PatientInfo patient;
    private MedecinInfo medecin;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatientInfo {
        private Long id;
        private String nom;
        private String prenom;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MedecinInfo {
        private Long id;
        private String nom;
        private String prenom;
    }
}
