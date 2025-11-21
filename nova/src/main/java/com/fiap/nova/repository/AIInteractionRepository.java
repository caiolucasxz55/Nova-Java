package com.fiap.nova.repository;

import com.fiap.nova.model.AIInteraction;
import com.fiap.nova.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIInteractionRepository extends JpaRepository<AIInteraction, Long> {
    
    Page<AIInteraction> findByUserId(Long userId, Pageable pageable);
    List<AIInteraction> findByUser(User user, Pageable pageable);
    
}
