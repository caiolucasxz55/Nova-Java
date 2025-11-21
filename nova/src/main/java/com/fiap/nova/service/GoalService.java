package com.fiap.nova.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.nova.model.Goal;
import com.fiap.nova.repository.GoalRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Page<Goal> listAllPaginated(Pageable pageable) {
        log.info("Fetching paginated goals from database");
        return goalRepository.findAll(pageable);
    }

    public List<Goal> listAll() {
        log.info("Fetching all goals from database");
        return goalRepository.findAll();
    }

    @Transactional
    public Goal createGoal(Goal goal, Long userId) {
        log.info("Saving goal to database: {} for userId: {}", goal, userId);
        // Aqui você pode adicionar lógica para vincular o goal ao user se necessário
        return goalRepository.save(goal);
    }

    public Goal getGoalById(Long id) {
        log.info("Fetching goal with id: {}", id);
        return goalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Goal not found with id: {}", id);
                    return new RuntimeException("Goal not found with id: " + id);
                });
    }

    @Transactional
    public Goal updateGoal(Long id, Goal goal) {
        log.info("Updating goal with id: {}", id);
        var existingGoal = getGoalById(id);

        // Atualiza os campos
        existingGoal.setTitle(goal.getTitle());
        existingGoal.setDescription(goal.getDescription());
        existingGoal.setCategory(goal.getCategory());
        existingGoal.setStatus(goal.getStatus());

        log.info("Saving updated goal: {}", existingGoal);
        return goalRepository.save(existingGoal);
    }

    @Transactional
    public void deleteGoal(Long id) {
        log.info("Deleting goal with id: {}", id);
        var goal = getGoalById(id);
        goalRepository.delete(goal);
        log.info("Goal deleted successfully with id: {}", id);
    }
}