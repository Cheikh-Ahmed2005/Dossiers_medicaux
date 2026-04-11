package com.medical.dossier.service;

import com.medical.dossier.dto.MedecinDTO;
import com.medical.dossier.entity.Medecin;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.MedecinRepository;
import com.medical.dossier.repository.ServiceHospitalierRepository;
import com.medical.dossier.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedecinService {

    private final MedecinRepository medecinRepository;
    private final ServiceHospitalierRepository serviceRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Transactional(readOnly = true)
    public List<MedecinDTO> findAll() {
        return medecinRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public MedecinDTO findById(Long id) {
        return toDTO(medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé : " + id)));
    }

    public Medecin findEntityById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé : " + id));
    }

    private MedecinDTO toDTO(Medecin m) {
        MedecinDTO dto = new MedecinDTO();
        dto.setId(m.getId());
        dto.setNom(m.getNom());
        dto.setPrenom(m.getPrenom());
        dto.setSpecialite(m.getSpecialite());
        if (m.getService() != null) {
            dto.setServiceId(m.getService().getId());
            dto.setService(new MedecinDTO.ServiceInfo(m.getService().getId(), m.getService().getNomService()));
        }
        if (m.getUtilisateur() != null) {
            dto.setUtilisateurId(m.getUtilisateur().getId());
        }
        return dto;
    }

    @Transactional
    public MedecinDTO save(MedecinDTO dto) {
        Medecin m = new Medecin();
        m.setNom(dto.getNom());
        m.setPrenom(dto.getPrenom());
        m.setSpecialite(dto.getSpecialite());
        if (dto.getServiceId() != null) {
            m.setService(serviceRepository.findById(dto.getServiceId()).orElse(null));
        }
        if (dto.getUtilisateurId() != null) {
            m.setUtilisateur(utilisateurRepository.findById(dto.getUtilisateurId()).orElse(null));
        }
        return toDTO(medecinRepository.save(m));
    }

    @Transactional
    public MedecinDTO update(Long id, MedecinDTO dto) {
        Medecin existing = findEntityById(id);
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setSpecialite(dto.getSpecialite());
        if (dto.getServiceId() != null) {
            existing.setService(serviceRepository.findById(dto.getServiceId()).orElse(null));
        } else {
            existing.setService(null);
        }
        return toDTO(medecinRepository.save(existing));
    }

    public void delete(Long id) {
        medecinRepository.deleteById(id);
    }
}
