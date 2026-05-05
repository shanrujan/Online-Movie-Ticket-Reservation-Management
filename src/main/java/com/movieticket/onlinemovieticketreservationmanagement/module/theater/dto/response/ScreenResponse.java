package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private Long theaterId;
    private String theaterName;
}