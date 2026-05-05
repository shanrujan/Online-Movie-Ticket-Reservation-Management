package com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.request;

import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.MovieStatus;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.MovieStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "Language is required")
    private String language;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private String posterUrl;

    private LocalDate releaseDate;

    @NotNull(message = "Status is required")
    private MovieStatus status;
}