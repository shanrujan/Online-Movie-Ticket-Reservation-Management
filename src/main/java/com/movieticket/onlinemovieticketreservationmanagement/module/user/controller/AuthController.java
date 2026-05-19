package com.movieticket.onlinemovieticketreservationmanagement.module.user.controller;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.LoginRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.request.RegisterRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response.AuthResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
* Controller class for handling authentication operation
* such as user login and registration
*/

@RestController//marks this class as a rest API controller
@RequestMapping("/api/auth")//Base URL for authentication endpoints
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor//automatically creates constructor for final fields
public class AuthController {

    //service layer object for authentication logic
    private final AuthService authService;


    //login endpoint
    //URL:POST/api/auth/login
    //@param request contain user email and password
    //@return JWT token or authentication response
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
    //Register endpoint
    //URL:POST/api/auth/Register
    //@param request contain user register details
    //@return authentication response after successful registration


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }
}