package com.fiap.nova.dto;

import jakarta.validation.constraints.NotBlank;

public record SkillRequest(
    
    @NotBlank(message = "Technical skill is required")
    String technicalSkill,
    
    String softSkill
    
) {}
