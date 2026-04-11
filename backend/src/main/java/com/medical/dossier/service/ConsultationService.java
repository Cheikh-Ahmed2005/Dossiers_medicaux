package com.medical.dossier.service;

import com.medical.dossier.dto.ConsultationDTO;
import com.medical.dossier.entity.Consultation;
import com.medical.dossier.entity.Medecin;
import com.medical.dossier.entity.Patient;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.ConsultationRepository;
import com.medical.dossier.repository.MedecinRepository;
import com.medical.dossier.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;

    @Transactional(readOnly = true)
    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ConsultationDTO findById(Long id) {
        return toDTO(consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée : " + id)));
    }

    @Transactional(readOnly = true)
    public List<ConsultationDTO> findByPatient(Long patientId) {
        return consultationRepository.findByPatientIdOrderByDateConsultationDesc(patientId)
                .stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ConsultationDTO> findByMedecin(Long medecinId) {
        return consultationRepository.findByMedecinIdOrderByDateConsultationDesc(medecinId)
                .stream().map(this::toDTO).toList();
    }

    @Transactional
    public ConsultationDTO save(ConsultationDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));
        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMedecin(medecin);
        consultation.setDateConsultation(dto.getDateConsultation() != null ? dto.getDateConsultation() : LocalDateTime.now());
        consultation.setDiagnostic(dto.getDiagnostic());
        consultation.setTraitement(dto.getTraitement());
        consultation.setNotes(dto.getNotes());

        return toDTO(consultationRepository.save(consultation));
    }

    @Transactional
    public ConsultationDTO update(Long id, ConsultationDTO dto) {
        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée : " + id));
        existing.setDiagnostic(dto.getDiagnostic());
        existing.setTraitement(dto.getTraitement());
        existing.setNotes(dto.getNotes());
        return toDTO(consultationRepository.save(existing));
    }

    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    private ConsultationDTO toDTO(Consultation c) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setId(c.getId());
        dto.setDateConsultation(c.getDateConsultation());
        dto.setDiagnostic(c.getDiagnostic());
        dto.setTraitement(c.getTraitement());
        dto.setNotes(c.getNotes());
        if (c.getPatient() != null) {
            dto.setPatientId(c.getPatient().getId());
            dto.setPatient(new ConsultationDTO.PatientInfo(
                c.getPatient().getId(), c.getPatient().getNom(), c.getPatient().getPrenom()
            ));
        }
        if (c.getMedecin() != null) {
            dto.setMedecinId(c.getMedecin().getId());
            dto.setMedecin(new ConsultationDTO.MedecinInfo(
                c.getMedecin().getId(), c.getMedecin().getNom(), c.getMedecin().getPrenom()
            ));
        }
        return dto;
    }
}