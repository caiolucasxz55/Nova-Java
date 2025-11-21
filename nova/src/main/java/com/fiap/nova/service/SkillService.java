package com.fiap.nova.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fiap.nova.model.Skill;
import com.fiap.nova.repository.SkillRepository;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> listAll() {
        return skillRepository.findAll();
    }

    public Page<Skill> listAllPaginated(Pageable pageable) {
        return skillRepository.findAll(pageable);
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElse(null);
    }

    public Skill updateSkill(Long id, Skill skillDetails) {
        Skill skill = getSkillById(id);
        skill.setTechnicalSkill(skillDetails.getTechnicalSkill());
        skill.setSoftSkill(skillDetails.getSoftSkill());
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        Skill skill = getSkillById(id);
        skillRepository.delete(skill);
    }
}