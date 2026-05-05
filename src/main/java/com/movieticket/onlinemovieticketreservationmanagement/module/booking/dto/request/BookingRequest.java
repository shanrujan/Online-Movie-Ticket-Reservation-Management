package com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Showtime ID is required")
    private Long showtimeId;

    @NotNull(message = "Seat IDs are required")
    private List<Long> seatIds;
}