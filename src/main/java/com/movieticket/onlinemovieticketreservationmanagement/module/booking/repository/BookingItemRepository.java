package com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
    List<BookingItem> findByBookingId(Long bookingId);
}