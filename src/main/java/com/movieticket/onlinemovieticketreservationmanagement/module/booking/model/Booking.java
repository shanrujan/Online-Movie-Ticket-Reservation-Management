package com.movieticket.onlinemovieticketreservationmanagement.module.booking.model;

import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Showtime;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<BookingItem> bookingItems;

    @PrePersist
    protected void onCreate() {
        bookingTime = LocalDateTime.now();
        if (status == null) {
            status = BookingStatus.PENDING;
        }
    }
}