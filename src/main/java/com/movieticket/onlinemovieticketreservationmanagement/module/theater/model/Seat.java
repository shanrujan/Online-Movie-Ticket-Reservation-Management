package com.movieticket.onlinemovieticketreservationmanagement.module.theater.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber; // e.g A1, B2, C3

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType; // REGULAR, PREMIUM, VIP

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;
}