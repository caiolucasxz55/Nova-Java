package com.fiap.nova.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GoalRequest(
    
    @NotBlank(message = "Goal title is required")
    String title,
    
    String description,
    
    @NotNull(message = "Category ID is required")
    Long categoryId,
    
    @NotNull(message = "Status ID is required")
    Long statusId
    
) {}
