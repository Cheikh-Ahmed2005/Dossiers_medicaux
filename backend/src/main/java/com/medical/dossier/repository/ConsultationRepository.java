package com.medical.dossier.repository;

import com.medical.dossier.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientIdOrderByDateConsultationDesc(Long patientId);
    List<Consultation> findByMedecinIdOrderByDateConsultationDesc(Long medecinId);
    long countByMedecinId(Long medecinId);
}
