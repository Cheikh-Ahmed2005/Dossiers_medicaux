package com.medical.dossier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsDTO {
    private long totalPatients;
    private long totalMedecins;
    private long totalConsultations;
    private long totalRendezVous;
    private long totalUtilisateurs;
    private long totalServices;
    private long rdvAujourdhui;
    private long consultationsAujourdhui;
}
