package com.fiap.nova.dto;

public record TokenResponse(
        String token,     // 1º ⬅️ TOKEN PRIMEIRO!
        String username,  // 2º
        String role       // 3º
) {}