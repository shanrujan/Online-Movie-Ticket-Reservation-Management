package com.movieticket.onlinemovieticketreservationmanagement.module.payment.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.request.PaymentRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.response.PaymentResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Process payment
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success(
                        "Payment processed successfully",
                        paymentService.processPayment(request)));
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.getPaymentById(id)));
    }

    // Get payment by booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getByBooking(
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        paymentService.getPaymentByBookingId(bookingId)));
    }

    // Get all payments - ADMIN only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.getAllPayments()));
    }

    // Refund payment - ADMIN only
    @PutMapping("/{id}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment refunded successfully",
                        paymentService.refundPayment(id)));
    }
}