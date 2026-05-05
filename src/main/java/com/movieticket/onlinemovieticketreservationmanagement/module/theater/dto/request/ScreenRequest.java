package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScreenRequest {

    @NotBlank(message = "Screen name is required")
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Theater ID is required")
    private Long theaterId;
}