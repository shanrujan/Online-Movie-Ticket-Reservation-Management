package com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieId(Long movieId);
    List<Showtime> findByScreenId(Long screenId);
    List<Showtime> findByStartTimeAfter(LocalDateTime time);
    List<Showtime> findByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime time);
}