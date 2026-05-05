package com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterResponse {
    private Long id;
    private String name;
    private String location;
    private String city;
    private String phone;
    private int totalScreens;
}