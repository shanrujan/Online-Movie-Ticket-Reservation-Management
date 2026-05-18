package com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponse {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Long screenId;
    private String screenName;
    private String theaterName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private int availableSeats;
}