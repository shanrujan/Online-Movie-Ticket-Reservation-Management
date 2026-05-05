package com.movieticket.onlinemovieticketreservationmanagement.module.user.dto.response;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String message;
    private String token;
    private Long userId;
    private String name;
    private Role role;
}