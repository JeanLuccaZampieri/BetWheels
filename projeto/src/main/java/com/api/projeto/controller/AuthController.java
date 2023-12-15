package com.api.projeto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.projeto.model.UsuarioModel;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioModel usuario) {
        // Lógica de autenticação (substitua isso com sua lógica real)
        if ("user@example.com".equals(usuario.getEmail()) && "senha".equals(usuario.getSenha())) {
            usuario.setAdministrador(false);
            return ResponseEntity.ok(usuario);
        } else if ("admin@example.com".equals(usuario.getEmail()) && "adminsenha".equals(usuario.getSenha())) {
            usuario.setAdministrador(true);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais incorretas");
        }
    }
}
