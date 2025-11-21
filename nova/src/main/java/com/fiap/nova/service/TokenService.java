package com.fiap.nova.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import com.fiap.nova.dto.TokenResponse;
import com.fiap.nova.model.User;

@Service
public class TokenService {

    private final JwtEncoder encoder;

    // Token válido por 24 horas
    private static final long TOKEN_EXPIRY_HOURS = 24;

    public TokenService(@Lazy JwtEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Gera um token JWT completo com TokenResponse
     */
    public TokenResponse generateToken(Authentication authentication) {
        Instant now = Instant.now();

        var role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("nova-api")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_EXPIRY_HOURS, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("role", role)
                .build();

        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        // ✅ Ordem correta: token, username, role
        return new TokenResponse(token, authentication.getName(), role);
    }

    /**
     * Gera apenas o token JWT (String) - usado para renovação
     */
    public String generateAccessTokenFromRefreshToken(User user) {
        Instant now = Instant.now();

        var role = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("nova-api")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_EXPIRY_HOURS, ChronoUnit.HOURS))
                .subject(user.getEmail())
                .claim("role", role)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Verifica se o token está próximo de expirar
     * Retorna true se faltarem menos de 2 horas para expirar
     */
    public boolean isTokenExpiringSoon(Instant expiration) {
        // Renova se faltar menos de 2 horas para expirar
        return expiration.isBefore(Instant.now().plus(2, ChronoUnit.HOURS));
    }
}