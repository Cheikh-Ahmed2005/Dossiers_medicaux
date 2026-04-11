package com.medical.dossier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class PrescriptionDTO {
    private Long id;

    @NotNull
    private Long consultationId;

    @NotBlank
    private String medicament;

    private String dosage;
    private String duree;
    private String instructions;

    // Objet imbriqué pour l'affichage frontend
    private ConsultationInfo consultation;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsultationInfo {
        private Long id;
        private LocalDateTime dateConsultation;
        private PatientInfo patient;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PatientInfo {
            private Long id;
            private String nom;
            private String prenom;
        }
    }
}
