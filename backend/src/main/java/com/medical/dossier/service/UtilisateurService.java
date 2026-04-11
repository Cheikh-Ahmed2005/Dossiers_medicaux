package com.medical.dossier.service;

import com.medical.dossier.dto.UtilisateurRequest;
import com.medical.dossier.entity.Utilisateur;
import com.medical.dossier.exception.ResourceNotFoundException;
import com.medical.dossier.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé : " + id));
    }

    public Utilisateur create(UtilisateurRequest request) {
        if (utilisateurRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username déjà utilisé");
        }
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Le mot de passe est obligatoire à la création");
        }
        Utilisateur user = Utilisateur.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .actif(request.isActif())
                .build();
        return utilisateurRepository.save(user);
    }

    public Utilisateur update(Long id, UtilisateurRequest request) {
        Utilisateur user = findById(id);

        if (!user.getUsername().equals(request.getUsername())
                && utilisateurRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username déjà utilisé");
        }
        if (!user.getEmail().equals(request.getEmail())
                && utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActif(request.isActif());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return utilisateurRepository.save(user);
    }

    public Utilisateur toggleActif(Long id) {
        Utilisateur u = findById(id);
        u.setActif(!u.isActif());
        return utilisateurRepository.save(u);
    }

    public void delete(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public void changePassword(Long id, String newPassword) {
        Utilisateur user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(user);
    }

    public void changeOwnPassword(String username, String currentPassword, String newPassword) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé : " + username));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(user);
    }
}
