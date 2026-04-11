package com.medical.dossier.repository;

import com.medical.dossier.entity.Specialiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialisteRepository extends JpaRepository<Specialiste, Long> {
    List<Specialiste> findByServiceId(Long serviceId);
}
