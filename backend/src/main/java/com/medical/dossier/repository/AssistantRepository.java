package com.medical.dossier.repository;

import com.medical.dossier.entity.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    List<Assistant> findByServiceId(Long serviceId);
}
