package com.sanskar.jobtrack.controller;

import com.sanskar.jobtrack.dto.AuthResponse;
import com.sanskar.jobtrack.dto.LoginRequest;
import com.sanskar.jobtrack.dto.RegisterRequest;
import com.sanskar.jobtrack.entity.User;
import com.sanskar.jobtrack.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Registration successful",
                        "userId", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}