package com.movieticket.onlinemovieticketreservationmanagement.module.movie.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Genre is required")
    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private int duration; // in minutes

    @Column(columnDefinition = "TEXT")
    private String description;

    private String posterUrl;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieStatus status;
}