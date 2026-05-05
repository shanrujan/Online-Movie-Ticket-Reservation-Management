package com.movieticket.onlinemovieticketreservationmanagement.module.payment.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.payment.model.Payment;
import com.movieticket.onlinemovieticketreservationmanagement.module.payment.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(Long bookingId);
    List<Payment> findByStatus(PaymentStatus status);
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByBookingId(Long bookingId);
}