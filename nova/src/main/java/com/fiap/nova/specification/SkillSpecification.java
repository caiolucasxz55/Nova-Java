package com.fiap.nova.specification;

import com.fiap.nova.filters.SkillFilters;
import com.fiap.nova.model.Skill;
import org.springframework.data.jpa.domain.Specification;

public class SkillSpecification {

    public static Specification<Skill> withFilters(SkillFilters filters) {
        return Specification
                .anyOf(
                        bySkillTecnica(filters.skillTecnica()),
                        bySoftSkill(filters.softSkill())
                );
    }

    private static Specification<Skill> bySkillTecnica(String skillTecnica) {
        if (skillTecnica == null || skillTecnica.isBlank()) return null;
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("skillTecnica")), "%" + skillTecnica.toLowerCase() + "%");
    }

    private static Specification<Skill> bySoftSkill(String softSkill) {
        if (softSkill == null || softSkill.isBlank()) return null;
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("softSkill")), "%" + softSkill.toLowerCase() + "%");
    }
    
}
