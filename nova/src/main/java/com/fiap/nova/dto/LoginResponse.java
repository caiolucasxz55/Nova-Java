package com.fiap.nova.dto;

public record LoginResponse(
        Long id,
        String name,
        String email,
        String professionalGoal,
        String role,
        TokenResponse tokens //
) {}
