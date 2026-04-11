package com.medical.dossier.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assistants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceHospitalier service;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", unique = true)
    private Utilisateur utilisateur;
}
