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
//control class for managing user-related operations
//registration and login

@RestController//marks this class as a REST controller
@RequestMapping("/api/users")//Base URL for user endpoint
@RequiredArgsConstructor//Generate constructor for final fields automatically
public class UserController {

    //service layer object for user operations
    private final UserService userService;

    //User register endpoint
    //request contain user register details
    //registers registered user information
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {//new user register to call service
        return ResponseEntity.status(201).body(userService.register(request));
    }
    //user login endpoint
    //@param request Contains email, passward
    //@return authentication response with token/details

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}