package com.movieticket.onlinemovieticketreservationmanagement.module.booking.model;

import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Seat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private double price;
}