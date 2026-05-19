package com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    List<BookingItem> findByBookingId(Long bookingId);

    @Query("""howtimeId
                                                                SELECT CASE WHEN COUNT(bi) > 0 THEN true ELSE false END
                                                                FROM BookingItem bi
                                                                WHERE bi.seat.id = :seatId
                                                                AND bi.booking.showtime.id = :s
            AND bi.booking.status = 'CONFIRMED'
            """)
    boolean existsBookedSeat(
            @Param("seatId") Long seatId,
            @Param("showtimeId") Long showtimeId
    );

    @Query("""
            SELECT bi.seat.id
            FROM BookingItem bi
            WHERE bi.booking.showtime.id = :showtimeId
            AND bi.booking.status = 'CONFIRMED'
            """)
    List<Long> getBookedSeatIds(
            @Param("showtimeId") Long showtimeId
    );
}