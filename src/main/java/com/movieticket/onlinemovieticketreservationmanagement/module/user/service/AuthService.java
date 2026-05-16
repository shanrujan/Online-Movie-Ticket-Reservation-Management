package com.movieticket.onlinemovieticketreservationmanagement.module.user.service;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.LoginRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.AuthResponse;
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

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .userId(user.getId())
                .name(user.getFullName())
                .role(user.getRole())
                .build();
    }
}