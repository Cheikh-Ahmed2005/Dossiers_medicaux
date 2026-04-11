package com.medical.dossier.controller;

import com.medical.dossier.dto.MedecinDTO;
import com.medical.dossier.service.MedecinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @GetMapping
    public ResponseEntity<List<MedecinDTO>> getAll() {
        return ResponseEntity.ok(medecinService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(medecinService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedecinDTO> create(@Valid @RequestBody MedecinDTO dto) {
        return ResponseEntity.ok(medecinService.save(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedecinDTO> update(@PathVariable Long id, @Valid @RequestBody MedecinDTO dto) {
        return ResponseEntity.ok(medecinService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medecinService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
