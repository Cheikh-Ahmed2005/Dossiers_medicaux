package com.medical.dossier.controller;

import com.medical.dossier.dto.RendezVousDTO;
import com.medical.dossier.entity.StatutRdv;
import com.medical.dossier.service.RendezVousService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendez-vous")
@RequiredArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAll() {
        return ResponseEntity.ok(rendezVousService.findAll());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVousDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(rendezVousService.findByPatient(patientId));
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<RendezVousDTO>> getByMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(rendezVousService.findByMedecin(medecinId));
    }

    @PostMapping
    public ResponseEntity<RendezVousDTO> create(@Valid @RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rendezVousService.save(dto));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<RendezVousDTO> updateStatut(@PathVariable Long id, @RequestParam StatutRdv statut) {
        return ResponseEntity.ok(rendezVousService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rendezVousService.delete(id);
        return ResponseEntity.noContent().build();
    }
}