package com.fiap.nova.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fiap.nova.model.AIInteraction;
import com.fiap.nova.service.AIInteractionService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ai-interactions")
@CrossOrigin(origins = "*")
public class AIInteractionController {

    private final AIInteractionService aiInteractionService;

    public AIInteractionController(AIInteractionService aiInteractionService) {
        this.aiInteractionService = aiInteractionService;
    }

    @GetMapping
    public PagedModel<EntityModel<AIInteraction>> getAll(
            @PageableDefault(size = 10, sort = "interactionDate") Pageable pageable,
            PagedResourcesAssembler<AIInteraction> assembler) {
        log.info("Listing paginated AI interactions - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        var page = aiInteractionService.listAllPaginated(pageable);
        return assembler.toModel(page, AIInteraction::toEntityModel);
    }

    @GetMapping("/all")
    public List<AIInteraction> listAll() {
        log.info("Listing all AI interactions");
        return aiInteractionService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AIInteraction createInteraction(@RequestBody @Valid AIInteraction interaction) {
        log.info("Creating AI interaction: {}", interaction);
        return aiInteractionService.createInteraction(interaction);
    }

    @GetMapping("/{id}")
    public EntityModel<AIInteraction> getById(@PathVariable Long id) {
        log.info("Getting AI interaction with id: {}", id);
        var interaction = aiInteractionService.getInteractionById(id);
        return interaction.toEntityModel();
    }
}
