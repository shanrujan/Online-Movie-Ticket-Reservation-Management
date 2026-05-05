package com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.response;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String genre;
    private String language;
    private int duration;
    private String description;
    private String posterUrl;
    private LocalDate releaseDate;
    private String status;
}