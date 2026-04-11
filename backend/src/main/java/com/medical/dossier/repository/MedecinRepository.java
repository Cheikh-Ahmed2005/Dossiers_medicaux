package com.medical.dossier.repository;

import com.medical.dossier.entity.Medecin;
import com.medical.dossier.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByUtilisateur(Utilisateur utilisateur);
    List<Medecin> findByServiceId(Long serviceId);
}
