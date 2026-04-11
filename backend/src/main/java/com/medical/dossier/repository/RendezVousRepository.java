package com.medical.dossier.repository;

import com.medical.dossier.entity.RendezVous;
import com.medical.dossier.entity.StatutRdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByMedecinId(Long medecinId);
    List<RendezVous> findByMedecinIdAndDateRdvBetween(Long medecinId, LocalDateTime from, LocalDateTime to);
    List<RendezVous> findByStatut(StatutRdv statut);
}
