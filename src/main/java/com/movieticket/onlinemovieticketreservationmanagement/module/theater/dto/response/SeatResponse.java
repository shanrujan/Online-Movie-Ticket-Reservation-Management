package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private String seatType;
    private Long screenId;
    private String screenName;
}