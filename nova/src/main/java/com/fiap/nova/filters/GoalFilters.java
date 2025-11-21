package com.fiap.nova.filters;

public record GoalFilters(
    String titulo,
    String descricao,
    Long categoriaId,
    Long statusId
) {}
