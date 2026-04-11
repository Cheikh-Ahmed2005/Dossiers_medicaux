package com.medical.dossier.service;

import com.medical.dossier.dto.ServiceDTO;
import com.medical.dossier.entity.ServiceHospitalier;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.ServiceHospitalierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceHospitalierService {

    private final ServiceHospitalierRepository serviceRepository;

    public List<ServiceHospitalier> findAll() {
        return serviceRepository.findAll();
    }

    public ServiceHospitalier save(ServiceDTO dto) {
        ServiceHospitalier s = ServiceHospitalier.builder()
                .nomService(dto.getNomService())
                .description(dto.getDescription())
                .build();
        return serviceRepository.save(s);
    }

    public ServiceHospitalier update(Long id, ServiceDTO dto) {
        ServiceHospitalier existing = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
        existing.setNomService(dto.getNomService());
        existing.setDescription(dto.getDescription());
        return serviceRepository.save(existing);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
