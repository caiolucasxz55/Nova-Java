package com.fiap.nova.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fiap.nova.model.Goal;
import com.fiap.nova.service.GoalService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public PagedModel<EntityModel<Goal>> getAll(
            @PageableDefault(size = 10, sort = "title") Pageable pageable,
            PagedResourcesAssembler<Goal> assembler) {
        log.info("Listing paginated goals - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        var page = goalService.listAllPaginated(pageable);
        return assembler.toModel(page, Goal::toEntityModel);
    }

    @GetMapping("/all")
    public List<Goal> listAll() {
        log.info("Listing all goals");
        return goalService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Goal createGoal(@RequestBody @Valid Goal goal, @RequestParam Long userId) {
        log.info("Creating goal: {} for userId: {}", goal, userId);
        return goalService.createGoal(goal, userId);
    }

    @GetMapping("/{id}")
    public EntityModel<Goal> getById(@PathVariable Long id) {
        log.info("Getting goal with id: {}", id);
        var goal = goalService.getGoalById(id);
        return goal.toEntityModel();
    }

    @PutMapping("/{id}")
    public EntityModel<Goal> updateGoal(@PathVariable Long id, @RequestBody @Valid Goal goal) {
        log.info("Updating goal with id: {}", id);
        var updatedGoal = goalService.updateGoal(id, goal);
        return updatedGoal.toEntityModel();
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        log.info("Deleting goal with id: {}", id);
        goalService.deleteGoal(id);
    }
}