package com.fiap.nova.repository;

import com.fiap.nova.model.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalStatusRepository extends JpaRepository<GoalStatus, Long> {
    
    Optional<GoalStatus> findByDescription(String description);
    
    boolean existsByDescription(String description);
    
}
