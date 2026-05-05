package com.movieticket.onlinemovieticketreservationmanagement.module.user.controller;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.LoginRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.RegisterRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.AuthResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.UserResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}