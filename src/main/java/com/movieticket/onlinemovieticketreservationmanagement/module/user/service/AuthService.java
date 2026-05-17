package com.movieticket.onlinemovieticketreservationmanagement.module.user.service;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.LoginRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.RegisterRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.AuthResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.Role;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.repository.UserRepository;
import com.movieticket.onlinemovieticketreservationmanagement.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ADMIN SECRET KEY
    private static final String ADMIN_SECRET_KEY = "ADMIN123";

    // REGISTER
    public AuthResponse register(RegisterRequest request) {

        // Check admin key FIRST
        if (request.getRole() == Role.ADMIN) {
            if (request.getAdminKey() == null || !request.getAdminKey().equals("ADMIN123")) {
                throw new RuntimeException("Incorrect Admin key entered");
            }
        }

        // Then check email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .message("Registration successful")
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found,incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}