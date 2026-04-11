package com.medical.dossier.controller;

import com.medical.dossier.dto.ChangeOwnPasswordRequest;
import com.medical.dossier.dto.LoginRequest;
import com.medical.dossier.dto.LoginResponse;
import com.medical.dossier.dto.RegisterRequest;
import com.medical.dossier.service.AuthService;
import com.medical.dossier.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UtilisateurService utilisateurService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(java.util.Map.of("message", "Utilisateur créé avec succès"));
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeOwnPassword(@Valid @RequestBody ChangeOwnPasswordRequest request,
                                               Principal principal) {
        utilisateurService.changeOwnPassword(principal.getName(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok(java.util.Map.of("message", "Mot de passe modifié avec succès"));
    }
}
