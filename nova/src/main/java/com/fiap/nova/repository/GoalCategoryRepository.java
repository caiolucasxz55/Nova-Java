package com.fiap.nova.repository;

import com.fiap.nova.model.GoalCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalCategoryRepository extends JpaRepository<GoalCategory, Long> {
    
    Optional<GoalCategory> findByDescription(String description);
    
    boolean existsByDescription(String description);
    
}
