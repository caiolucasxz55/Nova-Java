package com.fiap.nova.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fiap.nova.model.Skill;
import com.fiap.nova.service.SkillService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public PagedModel<EntityModel<Skill>> getAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable,
            PagedResourcesAssembler<Skill> assembler) {
        log.info("Listing paginated skills - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        var page = skillService.listAllPaginated(pageable);
        return assembler.toModel(page, Skill::toEntityModel);
    }

    @GetMapping("/all")
    public List<Skill> listAll() {
        log.info("Listing all skills");
        return skillService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(@RequestBody @Valid Skill skill) {
        log.info("Creating skill: {}", skill);
        return skillService.createSkill(skill);
    }

    @GetMapping("/{id}")
    public EntityModel<Skill> getById(@PathVariable Long id) {
        log.info("Getting skill with id: {}", id);
        var skill = skillService.getSkillById(id);
        return skill.toEntityModel();
    }

    @PutMapping("/{id}")
    public EntityModel<Skill> updateSkill(@PathVariable Long id, @RequestBody @Valid Skill skill) {
        log.info("Updating skill with id: {}", id);
        var updatedSkill = skillService.updateSkill(id, skill);
        return updatedSkill.toEntityModel();
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        log.info("Deleting skill with id: {}", id);
        skillService.deleteSkill(id);
    }
}