package com.medical.dossier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultationDTO {
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long medecinId;

    // Objets imbriqués pour l'affichage frontend
    private PatientInfo patient;
    private MedecinInfo medecin;

    private LocalDateTime dateConsultation;
    private String diagnostic;
    private String traitement;
    private String notes;

    private List<PrescriptionDTO> prescriptions;

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
