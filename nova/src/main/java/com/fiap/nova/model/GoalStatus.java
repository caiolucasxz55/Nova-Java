package com.fiap.nova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "NOVA_STATUS_META")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class GoalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_seq")
    @SequenceGenerator(name = "status_seq", sequenceName = "SEQ_STATUS_META", allocationSize = 1)
    @Column(name = "nova_id_status_meta")
    private Long id;

    @NotBlank(message = "Status description is required")
    @Column(name = "nova_descricao_status", nullable = false, length = 50, unique = true)
    private String description;

}
