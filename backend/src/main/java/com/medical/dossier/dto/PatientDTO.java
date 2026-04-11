package com.medical.dossier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private LocalDate dateNaissance;
    private String sexe;
    private String telephone;
    private String adresse;
    private String groupeSanguin;
    private String allergies;
}
