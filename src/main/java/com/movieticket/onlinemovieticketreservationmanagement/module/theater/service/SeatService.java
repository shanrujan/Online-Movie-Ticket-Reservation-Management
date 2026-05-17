package com.movieticket.onlinemovieticketreservationmanagement.module.theater.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.SeatRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.SeatResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Seat;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.ScreenRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository.BookingRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Showtime;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.ShowtimeRepository;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;

    // Get all seats by screen
    public List<SeatResponse> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreenId(screenId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get seats by showtime (with status)
    public List<SeatResponse> getSeatsByShowtime(Long showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id: " + showtimeId));
        Screen screen = showtime.getScreen();
        List<Seat> seats = seatRepository.findByScreenId(screen.getId());
        
        // Auto-generate missing seats (self-healing)
        if (seats.size() < 60) {
            String[] rows = {"A", "B", "C", "D", "E", "F"};
            for (String row : rows) {
                for (int i = 1; i <= 10; i++) {
                    String seatNum = row + i;
                    boolean exists = seats.stream().anyMatch(s -> s.getSeatNumber().equals(seatNum));
                    if (!exists) {
                        Seat newSeat = Seat.builder()
                                .seatNumber(seatNum)
                                .seatType(com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.SeatType.REGULAR)
                                .screen(screen)
                                .build();
                        seatRepository.save(newSeat);
                        seats.add(newSeat);
                    }
                }
            }
        }

        List<Long> bookedSeatIds = bookingRepository.findBookedSeatIdsByShowtimeId(showtimeId);
        
        return seats.stream().map(seat -> {
            SeatResponse response = mapToResponse(seat);
            if (bookedSeatIds.contains(seat.getId())) {
                response.setStatus("BOOKED");
            }
            return response;
        }).collect(Collectors.toList());
    }

    // Get seat by ID
    public SeatResponse getSeatById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));
        return mapToResponse(seat);
    }

    // Create seat
    public SeatResponse createSeat(SeatRequest request) {
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with id: " + request.getScreenId()));

        if (seatRepository.existsBySeatNumberAndScreenId(
                request.getSeatNumber(), request.getScreenId())) {
            throw new BadRequestException(
                    "Seat already exists: " + request.getSeatNumber());
        }

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .seatType(request.getSeatType())
                .screen(screen)
                .build();

        return mapToResponse(seatRepository.save(seat));
    }

    // Delete seat
    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Seat not found with id: " + id);
        }
        seatRepository.deleteById(id);
    }

    // Mapper
    private SeatResponse mapToResponse(Seat seat) {
        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getSeatType().name(),
                seat.getScreen().getId(),
                seat.getScreen().getName(),
                "AVAILABLE"
        );
    }
}