package com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private String userName;
    private String movieTitle;
    private double amount;
    private String paymentMethod;
    private String transactionId;
    private String status;
    private LocalDateTime paymentTime;
}