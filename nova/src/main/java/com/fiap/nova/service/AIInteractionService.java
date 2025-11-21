package com.fiap.nova.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fiap.nova.model.AIInteraction;
import com.fiap.nova.repository.AIInteractionRepository;

import java.util.List;

@Service
public class AIInteractionService {

    private final AIInteractionRepository aiInteractionRepository;


    public AIInteractionService(AIInteractionRepository aiInteractionRepository) {
        this.aiInteractionRepository = aiInteractionRepository;
    }

    public AIInteraction createInteraction(AIInteraction interaction) {
        return aiInteractionRepository.save(interaction);
    }

    public List<AIInteraction> listAll() {
        return aiInteractionRepository.findAll();
    }

    public Page<AIInteraction> listAllPaginated(Pageable pageable) {
        return aiInteractionRepository.findAll(pageable);
    }

    public AIInteraction getInteractionById(Long id) {
        return aiInteractionRepository.findById(id)
                .orElse(null);
    }
}
