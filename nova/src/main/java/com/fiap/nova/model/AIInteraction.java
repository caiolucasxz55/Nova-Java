package com.fiap.nova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOVA_INTERACAO_IA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AIInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interacao_seq")
    @SequenceGenerator(name = "interacao_seq", sequenceName = "seq_interacao_ia", allocationSize = 1)
    @Column(name = "nova_id_interacao")
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "nova_id_usuario", nullable = false)
    private User user;

    @Column(name = "nova_mensagem_usuario", length = 4000)
    private String userMessage;

    @Column(name = "nova_resposta_ia", length = 4000)
    private String aiResponse;

    @CreationTimestamp
    @Column(name = "nova_data_interacao", updatable = false)
    private LocalDateTime interactionDate;

    public EntityModel<AIInteraction> toEntityModel() {
        return EntityModel.of(this);
    }

}
