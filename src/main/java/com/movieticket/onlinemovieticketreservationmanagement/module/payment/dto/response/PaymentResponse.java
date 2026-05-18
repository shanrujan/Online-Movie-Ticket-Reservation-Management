package com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    // Unique payment ID
    private Long id;
    // Related booking ID
    private Long bookingId;
    // Name of the user who made the payment
    private String userName;
    // Movie title related to the booking
    private String movieTitle;
    // Total payment amount
    private double amount;
    // Payment method used (CARD, CASH, UPI)
    private String paymentMethod;
    // Unique transaction ID
    private String transactionId;
    // Current payment status
    private String status;
    // Payment date and time
    private LocalDateTime paymentTime;
}