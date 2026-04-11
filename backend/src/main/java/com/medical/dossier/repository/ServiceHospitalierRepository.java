package com.medical.dossier.repository;

import com.medical.dossier.entity.ServiceHospitalier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceHospitalierRepository extends JpaRepository<ServiceHospitalier, Long> {
}
