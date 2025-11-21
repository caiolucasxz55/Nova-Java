package com.fiap.nova.dto;

public record GoalResponse(
        Long id,
        String title,
        String description,
        String category,
        String status
) {}

