package com.fiap.nova.controller;

import com.fiap.nova.dto.LoginRequest;
import com.fiap.nova.dto.LoginResponse;
import com.fiap.nova.dto.TokenResponse;
import com.fiap.nova.model.User;
import com.fiap.nova.service.AuthService;
import com.fiap.nova.service.TokenService;
import com.fiap.nova.service.UserService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;
    private final AuthService authService;  // ⬅️ ADICIONE ISSO
    private final AuthenticationManager authenticationManager;

    public AuthController(
            TokenService tokenService,
            UserService userService,
            AuthService authService,  // ⬅️ ADICIONE ISSO
            AuthenticationManager authenticationManager
    ) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authService = authService;  // ⬅️ ADICIONE ISSO
        this.authenticationManager = authenticationManager;
    }

    // ---------------------- LOGIN ----------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.email() == null || request.password() == null) {
                return ResponseEntity.badRequest().body("Email e senha são obrigatórios");
            }

            // 🔐 Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            // Recupera UserDetails
            User user = (User) authentication.getPrincipal();

            // Gera token
            TokenResponse tokenResponse = tokenService.generateToken(authentication);

            // 🔍 LOG DE DEBUG
            System.out.println("🔑 TOKEN GERADO NO LOGIN:");
            System.out.println("Token: " + tokenResponse.token());
            System.out.println("Tamanho: " + tokenResponse.token().length());
            System.out.println("Username: " + tokenResponse.username());
            System.out.println("Role: " + tokenResponse.role());

            // Monta a resposta completa
            LoginResponse response = new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getProfessionalGoal(),
                    user.getRole(),
                    tokenResponse
            );

            System.out.println("📤 RESPONSE COMPLETO: " + response);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro no servidor ao autenticar");
        }
    }

    // ---------------------- REGISTER ----------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Validação básica
            if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
                return ResponseEntity.badRequest()
                        .body("Nome, email e senha são obrigatórios");
            }

            if (user.getProfessionalGoal() == null || user.getProfessionalGoal().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Objetivo profissional é obrigatório");
            }

            // 1️⃣ Salva o usuário no banco (senha será criptografada)
            User savedUser = userService.createUser(user);

            // 2️⃣ Busca o usuário recém-salvo usando AuthService
            User userFromDb = (User) authService.loadUserByUsername(savedUser.getEmail());

            // 3️⃣ Cria autenticação
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userFromDb,
                    null,
                    userFromDb.getAuthorities()
            );

            // 4️⃣ Gera token
            TokenResponse tokenResponse = tokenService.generateToken(auth);

            // 🔍 LOG DE DEBUG
            System.out.println("🔑 TOKEN GERADO NO REGISTER:");
            System.out.println("Token: " + tokenResponse.token());
            System.out.println("Tamanho: " + tokenResponse.token().length());
            System.out.println("Username: " + tokenResponse.username());
            System.out.println("Role: " + tokenResponse.role());

            // 5️⃣ Monta LoginResponse completo
            LoginResponse response = new LoginResponse(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getProfessionalGoal(),
                    savedUser.getRole(),
                    tokenResponse
            );

            System.out.println("📤 RESPONSE COMPLETO DO REGISTER: " + response);

            return ResponseEntity.ok(response);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400)
                    .body("Email já cadastrado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao registrar usuário: " + e.getMessage());
        }
    }
}