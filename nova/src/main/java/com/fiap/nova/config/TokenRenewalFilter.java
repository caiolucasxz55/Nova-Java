package com.fiap.nova.config;

import com.fiap.nova.dto.TokenResponse;
import com.fiap.nova.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class TokenRenewalFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    // Adiciona @Lazy para quebrar o ciclo de dependência
    public TokenRenewalFilter(@Lazy TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // Verifica se está autenticado e é um JWT
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                Jwt jwt = jwtAuth.getToken();
                Instant expiration = jwt.getExpiresAt();

                // Se o token está próximo de expirar, gera um novo
                if (expiration != null && tokenService.isTokenExpiringSoon(expiration)) {
                    TokenResponse newToken = tokenService.generateToken(auth);

                    // Adiciona o novo token no header da resposta
                    response.setHeader("X-New-Token", newToken.token());
                    response.setHeader("Access-Control-Expose-Headers", "X-New-Token");

                    logger.info("✅ Token renovado automaticamente para: " + auth.getName());
                }
            }
        } catch (Exception e) {
            // Se der erro, apenas continua sem renovar
            logger.debug("Erro ao verificar renovação de token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}