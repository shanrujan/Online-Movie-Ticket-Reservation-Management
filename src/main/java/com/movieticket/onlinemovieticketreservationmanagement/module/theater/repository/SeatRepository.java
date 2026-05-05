package com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Seat;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenId(Long screenId);
    List<Seat> findByScreenIdAndSeatType(Long screenId, SeatType seatType);
    long countByScreenId(Long screenId);
    boolean existsBySeatNumberAndScreenId(String seatNumber, Long screenId);
}