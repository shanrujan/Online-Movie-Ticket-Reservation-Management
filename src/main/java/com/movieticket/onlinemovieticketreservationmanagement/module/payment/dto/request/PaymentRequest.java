package com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    // Stores booking ID related to the payment
    @NotNull(message = "Booking ID is required")
    private Long bookingId;
    // Stores payment method such as CARD, CASH, or UPI
    @NotBlank(message = "Payment method is required")
    private String paymentMethod; // CARD, CASH, UPI
}
