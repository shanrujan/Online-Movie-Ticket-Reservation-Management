package com.movieticket.onlinemovieticketreservationmanagement.module.payment.model;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    // Primary key for payment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-one relationship with Booking entity
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // Total payment amount
    @Column(nullable = false)
    private double amount;

    // Payment method such as CARD, CASH, or UPI
    @Column(nullable = false)
    private String paymentMethod;

    // Unique transaction ID
    @Column(unique = true)
    private String transactionId;

    // Payment status stored as String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    // Stores payment date and time
    private LocalDateTime paymentTime;

    // Automatically runs before saving payment
    @PrePersist
    protected void onCreate() {

        // Set current payment time
        paymentTime = LocalDateTime.now();

        // Set default payment status
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
}