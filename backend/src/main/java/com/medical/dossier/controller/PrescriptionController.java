package com.medical.dossier.controller;

import com.medical.dossier.dto.PrescriptionDTO;
import com.medical.dossier.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<PrescriptionDTO>> getAll() {
        return ResponseEntity.ok(prescriptionService.findAll());
    }

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<PrescriptionDTO>> getByConsultation(@PathVariable Long consultationId) {
        return ResponseEntity.ok(prescriptionService.findByConsultation(consultationId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'ADMIN')")
    public ResponseEntity<PrescriptionDTO> create(@Valid @RequestBody PrescriptionDTO dto) {
        return ResponseEntity.ok(prescriptionService.save(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'ADMIN')")
    public ResponseEntity<PrescriptionDTO> update(@PathVariable Long id, @Valid @RequestBody PrescriptionDTO dto) {
        return ResponseEntity.ok(prescriptionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prescriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}