package com.medical.dossier.service;

import com.medical.dossier.dto.PrescriptionDTO;
import com.medical.dossier.entity.Prescription;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.ConsultationRepository;
import com.medical.dossier.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> findAll() {
        return prescriptionRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> findByConsultation(Long consultationId) {
        return prescriptionRepository.findByConsultationId(consultationId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public PrescriptionDTO save(PrescriptionDTO dto) {
        Prescription p = new Prescription();
        p.setConsultation(consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée")));
        p.setMedicament(dto.getMedicament());
        p.setDosage(dto.getDosage());
        p.setDuree(dto.getDuree());
        p.setInstructions(dto.getInstructions());
        return toDTO(prescriptionRepository.save(p));
    }

    @Transactional
    public PrescriptionDTO update(Long id, PrescriptionDTO dto) {
        Prescription existing = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription non trouvée"));
        existing.setMedicament(dto.getMedicament());
        existing.setDosage(dto.getDosage());
        existing.setDuree(dto.getDuree());
        existing.setInstructions(dto.getInstructions());
        return toDTO(prescriptionRepository.save(existing));
    }

    public void delete(Long id) {
        prescriptionRepository.deleteById(id);
    }

    private PrescriptionDTO toDTO(Prescription p) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(p.getId());
        dto.setMedicament(p.getMedicament());
        dto.setDosage(p.getDosage());
        dto.setDuree(p.getDuree());
        dto.setInstructions(p.getInstructions());
        if (p.getConsultation() != null) {
            dto.setConsultationId(p.getConsultation().getId());
            PrescriptionDTO.ConsultationInfo cInfo = new PrescriptionDTO.ConsultationInfo();
            cInfo.setId(p.getConsultation().getId());
            cInfo.setDateConsultation(p.getConsultation().getDateConsultation());
            if (p.getConsultation().getPatient() != null) {
                cInfo.setPatient(new PrescriptionDTO.ConsultationInfo.PatientInfo(
                    p.getConsultation().getPatient().getId(),
                    p.getConsultation().getPatient().getNom(),
                    p.getConsultation().getPatient().getPrenom()
                ));
            }
            dto.setConsultation(cInfo);
        }
        return dto;
    }
}