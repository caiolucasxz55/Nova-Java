package com.fiap.nova.controller;

import com.fiap.nova.service.PerplexityService;
import com.fiap.nova.service.AuthService;
import com.fiap.nova.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final PerplexityService perplexityService;
    private final AuthService authService;

    public ChatbotController(PerplexityService perplexityService, AuthService authService) {
        this.perplexityService = perplexityService;
        this.authService = authService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> ask(@RequestBody Map<String, Object> json) {
        try {
            // ✅ Validação dos campos
            if (json == null || !json.containsKey("message")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Campo 'message' é obrigatório"));
            }

            Object messageObj = json.get("message");
            if (messageObj == null || messageObj.toString().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Mensagem não pode estar vazia"));
            }

            String message = messageObj.toString();

            // ✅ Pega o userId do token JWT
            Long userId = getUserIdFromToken();

            if (userId == null) {
                System.out.println("❌ Não foi possível extrair userId do token");
                return ResponseEntity.status(401)
                        .body(Map.of("error", "Usuário não autenticado"));
            }

            System.out.println("🤖 Chatbot - User ID: " + userId);
            System.out.println("💬 Mensagem: " + message);

            // Processa a pergunta
            String answer = perplexityService.chatWithAI(userId, message);

            return ResponseEntity.ok(Map.of("answer", answer));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Erro ao processar mensagem: " + e.getMessage()));
        }
    }

    /**
     * Extrai o userId do token JWT atual
     */
    private Long getUserIdFromToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
                // ✅ O Principal é um Jwt, não um User
                Jwt jwt = (Jwt) authentication.getPrincipal();

                // Pega o email do subject do JWT
                String email = jwt.getSubject();

                System.out.println("📧 Email do token: " + email);

                // Busca o usuário pelo email
                User user = (User) authService.loadUserByUsername(email);

                System.out.println("👤 Usuário encontrado: " + user.getName() + " (ID: " + user.getId() + ")");

                return user.getId();
            }

            System.out.println("⚠️ Authentication principal não é Jwt: " +
                    (authentication != null ? authentication.getPrincipal().getClass() : "null"));

            return null;
        } catch (Exception e) {
            System.out.println("❌ Erro ao extrair userId: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}