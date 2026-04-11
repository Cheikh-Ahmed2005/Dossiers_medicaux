package com.medical.dossier.dto;

import com.medical.dossier.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UtilisateurDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private boolean actif;
    private LocalDateTime createdAt;
}
