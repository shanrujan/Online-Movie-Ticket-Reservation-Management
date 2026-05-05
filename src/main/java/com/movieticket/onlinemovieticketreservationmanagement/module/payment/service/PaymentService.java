package com.movieticket.onlinemovieticketreservationmanagement.module.payment.service;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository.BookingRepository;
import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingStatus;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.request.PaymentRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.dto.response.PaymentResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.model.Payment;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.model.PaymentStatus;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    // Process payment
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        // Check booking exists
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        // Check booking is not cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException(
                    "Cannot process payment for cancelled booking");
        }

        // Check payment already exists
        if (paymentRepository.existsByBookingId(request.getBookingId())) {
            throw new BadRequestException(
                    "Payment already exists for this booking");
        }

        // Create payment
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .transactionId(UUID.randomUUID().toString())
                .status(PaymentStatus.SUCCESS)
                .build();

        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }

    // Get payment by ID
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    // Get payment by booking ID
    public PaymentResponse getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found for booking id: " + bookingId));
        return mapToResponse(payment);
    }

    // Get all payments (admin)
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Refund payment
    @Transactional
    public PaymentResponse refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + id));

        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new BadRequestException("Payment already refunded");
        }

        if (payment.getStatus() == PaymentStatus.FAILED) {
            throw new BadRequestException("Cannot refund a failed payment");
        }

        payment.setStatus(PaymentStatus.REFUNDED);

        // Also cancel the booking
        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return mapToResponse(paymentRepository.save(payment));
    }

    // Mapper
    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getBooking().getUser().getName(),
                payment.getBooking().getShowtime().getMovie().getTitle(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getTransactionId(),
                payment.getStatus().name(),
                payment.getPaymentTime()
        );
    }
}