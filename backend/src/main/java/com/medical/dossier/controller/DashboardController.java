package com.medical.dossier.controller;

import com.medical.dossier.dto.DashboardStatsDTO;
import com.medical.dossier.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final ConsultationRepository consultationRepository;
    private final RendezVousRepository rendezVousRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ServiceHospitalierRepository serviceRepository;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        DashboardStatsDTO stats = DashboardStatsDTO.builder()
                .totalPatients(patientRepository.count())
                .totalMedecins(medecinRepository.count())
                .totalConsultations(consultationRepository.count())
                .totalRendezVous(rendezVousRepository.count())
                .totalUtilisateurs(utilisateurRepository.count())
                .totalServices(serviceRepository.count())
                .build();
        return ResponseEntity.ok(stats);
    }
}
