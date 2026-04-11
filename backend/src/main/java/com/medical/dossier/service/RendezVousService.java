package com.medical.dossier.service;

import com.medical.dossier.dto.RendezVousDTO;
import com.medical.dossier.entity.Medecin;
import com.medical.dossier.entity.Patient;
import com.medical.dossier.entity.RendezVous;
import com.medical.dossier.entity.StatutRdv;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.MedecinRepository;
import com.medical.dossier.repository.PatientRepository;
import com.medical.dossier.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;

    @Transactional(readOnly = true)
    public List<RendezVousDTO> findAll() {
        return rendezVousRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<RendezVousDTO> findByPatient(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<RendezVousDTO> findByMedecin(Long medecinId) {
        return rendezVousRepository.findByMedecinId(medecinId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public RendezVousDTO save(RendezVousDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));
        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));

        RendezVous rdv = new RendezVous();
        rdv.setPatient(patient);
        rdv.setMedecin(medecin);
        rdv.setDateRdv(dto.getDateRdv());
        rdv.setMotif(dto.getMotif());
        rdv.setStatut(StatutRdv.PLANIFIE);
        return toDTO(rendezVousRepository.save(rdv));
    }

    @Transactional
    public RendezVousDTO updateStatut(Long id, StatutRdv statut) {
        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous non trouvé"));
        rdv.setStatut(statut);
        return toDTO(rendezVousRepository.save(rdv));
    }

    public void delete(Long id) {
        rendezVousRepository.deleteById(id);
    }

    private RendezVousDTO toDTO(RendezVous rdv) {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setId(rdv.getId());
        dto.setDateRdv(rdv.getDateRdv());
        dto.setStatut(rdv.getStatut());
        dto.setMotif(rdv.getMotif());
        if (rdv.getPatient() != null) {
            dto.setPatientId(rdv.getPatient().getId());
            dto.setPatient(new RendezVousDTO.PatientInfo(
                rdv.getPatient().getId(), rdv.getPatient().getNom(), rdv.getPatient().getPrenom()
            ));
        }
        if (rdv.getMedecin() != null) {
            dto.setMedecinId(rdv.getMedecin().getId());
            dto.setMedecin(new RendezVousDTO.MedecinInfo(
                rdv.getMedecin().getId(), rdv.getMedecin().getNom(), rdv.getMedecin().getPrenom()
            ));
        }
        return dto;
    }
}