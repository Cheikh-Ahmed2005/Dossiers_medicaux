package com.medical.dossier.service;

import com.medical.dossier.dto.PatientDTO;
import com.medical.dossier.entity.Patient;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public PatientDTO findById(Long id) {
        return toDTO(patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé : " + id)));
    }

    public Patient findEntityById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé : " + id));
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> search(String query) {
        return patientRepository.search(query).stream().map(this::toDTO).toList();
    }

    public PatientDTO save(PatientDTO dto) {
        return toDTO(patientRepository.save(toEntity(dto)));
    }

    public PatientDTO update(Long id, PatientDTO dto) {
        Patient existing = findEntityById(id);
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setSexe(dto.getSexe());
        existing.setTelephone(dto.getTelephone());
        existing.setAdresse(dto.getAdresse());
        existing.setGroupeSanguin(dto.getGroupeSanguin());
        existing.setAllergies(dto.getAllergies());
        return toDTO(patientRepository.save(existing));
    }

    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient non trouvé : " + id);
        }
        patientRepository.deleteById(id);
    }

    private PatientDTO toDTO(Patient p) {
        PatientDTO dto = new PatientDTO();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setPrenom(p.getPrenom());
        dto.setDateNaissance(p.getDateNaissance());
        dto.setSexe(p.getSexe());
        dto.setTelephone(p.getTelephone());
        dto.setAdresse(p.getAdresse());
        dto.setGroupeSanguin(p.getGroupeSanguin());
        dto.setAllergies(p.getAllergies());
        return dto;
    }

    private Patient toEntity(PatientDTO dto) {
        return Patient.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .sexe(dto.getSexe())
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .groupeSanguin(dto.getGroupeSanguin())
                .allergies(dto.getAllergies())
                .build();
    }
}