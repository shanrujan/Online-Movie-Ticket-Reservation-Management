package com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowtimeId(Long showtimeId);

    boolean existsByShowtimeIdAndBookingItemsSeatIdAndStatus(
            Long showtimeId,
            Long seatId,
            BookingStatus status
    );

    @org.springframework.data.jpa.repository.Query("SELECT bi.seat.id FROM Booking b JOIN b.bookingItems bi WHERE b.showtime.id = :showtimeId AND b.status = 'CONFIRMED'")
    List<Long> findBookedSeatIdsByShowtimeId(@org.springframework.data.repository.query.Param("showtimeId") Long showtimeId);
}