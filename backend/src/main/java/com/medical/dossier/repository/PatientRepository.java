package com.medical.dossier.repository;

import com.medical.dossier.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR LOWER(p.prenom) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR p.telephone LIKE CONCAT('%', :q, '%')")
    List<Patient> search(@Param("q") String query);
}
