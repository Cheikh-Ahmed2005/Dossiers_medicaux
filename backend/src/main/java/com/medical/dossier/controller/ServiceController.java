package com.medical.dossier.controller;

import com.medical.dossier.dto.ServiceDTO;
import com.medical.dossier.entity.ServiceHospitalier;
import com.medical.dossier.service.ServiceHospitalierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceHospitalierService serviceHospitalierService;

    @GetMapping
    public ResponseEntity<List<ServiceHospitalier>> getAll() {
        return ResponseEntity.ok(serviceHospitalierService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceHospitalier> create(@Valid @RequestBody ServiceDTO dto) {
        return ResponseEntity.ok(serviceHospitalierService.save(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceHospitalier> update(@PathVariable Long id, @Valid @RequestBody ServiceDTO dto) {
        return ResponseEntity.ok(serviceHospitalierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceHospitalierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
