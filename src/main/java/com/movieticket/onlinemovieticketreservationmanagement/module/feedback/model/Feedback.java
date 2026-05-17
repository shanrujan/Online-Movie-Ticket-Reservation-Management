// module/feedback/model/Feedback.java
package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.model;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Movie;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private int rating; // 1 to 5

    private String comment;

    private LocalDateTime createdAt;
}