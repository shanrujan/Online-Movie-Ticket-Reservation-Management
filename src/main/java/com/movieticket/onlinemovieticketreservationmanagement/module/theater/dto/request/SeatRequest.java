package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request;

import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatRequest {

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Seat type is required")
    private SeatType seatType;

    @NotNull(message = "Screen ID is required")
    private Long screenId;
}