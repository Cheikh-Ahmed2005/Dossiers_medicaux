package com.medical.dossier.controller;

import com.medical.dossier.dto.ConsultationDTO;
import com.medical.dossier.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<ConsultationDTO>> getAll() {
        return ResponseEntity.ok(consultationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(consultationService.findByPatient(patientId));
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<ConsultationDTO>> getByMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(consultationService.findByMedecin(medecinId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'SPECIALISTE', 'ADMIN')")
    public ResponseEntity<ConsultationDTO> create(@Valid @RequestBody ConsultationDTO dto) {
        return ResponseEntity.ok(consultationService.save(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'SPECIALISTE', 'ADMIN')")
    public ResponseEntity<ConsultationDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultationDTO dto) {
        return ResponseEntity.ok(consultationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'SPECIALISTE', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}