package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TheaterRequest {

    @NotBlank(message = "Theater name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "City is required")
    private String city;

    private String phone;
}