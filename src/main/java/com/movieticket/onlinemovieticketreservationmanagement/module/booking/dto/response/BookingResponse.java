// BookingResponse.java
package com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private String userName;
    private String movieTitle;
    private String theaterName;
    private LocalDateTime showtime;
    private List<String> seatNumbers;
    private double totalAmount;
    private String status;
    private LocalDateTime bookingTime;
}