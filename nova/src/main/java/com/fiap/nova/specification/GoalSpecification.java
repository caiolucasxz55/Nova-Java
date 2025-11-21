package com.fiap.nova.specification;

import com.fiap.nova.filters.GoalFilters;
import com.fiap.nova.model.Goal;
import org.springframework.data.jpa.domain.Specification;

public class GoalSpecification {

    public static Specification<Goal> withFilters(GoalFilters filters) {
        return Specification
                .anyOf(
                        byTitulo(filters.titulo()),
                        byDescricao(filters.descricao()),
                        byCategoriaId(filters.categoriaId()),
                        byStatusId(filters.statusId())
                );
    }

    private static Specification<Goal> byTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) return null;
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    private static Specification<Goal> byDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) return null;
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
    }

    private static Specification<Goal> byCategoriaId(Long categoriaId) {
        if (categoriaId == null) return null;
        return (root, query, cb) -> 
            cb.equal(root.get("categoria").get("id"), categoriaId);
    }

    private static Specification<Goal> byStatusId(Long statusId) {
        if (statusId == null) return null;
        return (root, query, cb) -> 
            cb.equal(root.get("status").get("id"), statusId);
    }
    
}
