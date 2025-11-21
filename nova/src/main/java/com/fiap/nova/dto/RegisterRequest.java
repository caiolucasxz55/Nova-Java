package com.fiap.nova.dto;

import com.fiap.nova.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 200)
    String name,
    
    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 200)
    String email,
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100)
    String password,
    
    @NotBlank(message = "Professional goal is required")
    @Size(max = 255)
    String professionalGoal
) {
    public User toModel() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .professionalGoal(professionalGoal)
                .role("ROLE_USER")
                .build();
    }
}
