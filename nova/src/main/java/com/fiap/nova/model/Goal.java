package com.fiap.nova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "NOVA_METAS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meta_seq")
    @SequenceGenerator(name = "meta_seq", sequenceName = "SEQ_METAS", allocationSize = 1)
    @Column(name = "nova_id_meta")
    private Long id;

    @NotBlank(message = "Goal title is required")
    @Column(name = "nova_titulo_meta", nullable = false, length = 100)
    private String title;

    @Column(name = "nova_descricao_meta", length = 255)
    private String description;

    @NotNull(message = "Goal category is required")
    @ManyToOne
    @JoinColumn(name = "nova_id_categoria_meta", nullable = false)
    private GoalCategory category;

    @NotNull(message = "Goal status is required")
    @ManyToOne
    @JoinColumn(name = "nova_id_status_meta", nullable = false)
    private GoalStatus status;

    public EntityModel<Goal> toEntityModel() {
        return EntityModel.of(this);
    }

}
