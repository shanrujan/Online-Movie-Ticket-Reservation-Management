package com.movieticket.onlinemovieticketreservationmanagement.module.user.controller;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.LoginRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.AuthResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}