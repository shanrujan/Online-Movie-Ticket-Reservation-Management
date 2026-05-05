package com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Movie;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByStatus(MovieStatus status);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguage(String language);
    boolean existsByTitle(String title);
}