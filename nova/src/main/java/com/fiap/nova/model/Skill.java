package com.fiap.nova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "NOVA_SKILLS_GOALS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq")
    @SequenceGenerator(name = "skill_seq", sequenceName = "SEQ_SKILLS_GOALS", allocationSize = 1)
    @Column(name = "nova_id_habilidade")
    private Long id;

    @NotBlank(message = "Technical skill is required")
    @Column(name = "nova_skill_tecnica", nullable = false, length = 100, unique = true)
    private String technicalSkill;

    @Column(name = "nova_soft_skill", length = 100)
    private String softSkill;

    public EntityModel<Skill> toEntityModel() {
        return EntityModel.of(this);
    }

}
