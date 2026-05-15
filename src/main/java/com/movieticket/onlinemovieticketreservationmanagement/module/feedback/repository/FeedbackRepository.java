// module/feedback/repository/FeedbackRepository.java
package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMovieId(Long movieId);

    List<Feedback> findByUserId(Long userId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    boolean existsByBookingId(Long bookingId);
}
