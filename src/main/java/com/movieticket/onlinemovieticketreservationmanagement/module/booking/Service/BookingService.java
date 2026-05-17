package com.movieticket.onlinemovieticketreservationmanagement.module.booking.service;

import com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository.BookingRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository.BookingItemRepository;
import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.request.BookingRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.response.BookingResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingItem;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.BookingStatus;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Showtime;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.ShowtimeRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Seat;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.SeatRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    // ─── Create Booking ───────────────────────────────
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        // Validate user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Validate showtime
        Showtime showtime = showtimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

        // Validate seats
        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new BadRequestException("At least one seat must be selected");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        // Calculate total amount
        double totalAmount = 0;
        List<BookingItem> bookingItems = new ArrayList<>();

        for (Long seatId : request.getSeatIds()) {

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Seat not found with id: " + seatId));

                  boolean alreadyBooked = bookingRepository.existsByShowtimeIdAndBookingItemsSeatIdAndStatus(
                    request.getShowtimeId(),
                    seatId,
                    BookingStatus.CONFIRMED
            );

            if (alreadyBooked) {
                throw new BadRequestException(
                        "Seat " + seat.getSeatNumber() + " is already booked");
            }

            BookingItem item = new BookingItem();
            item.setBooking(booking);
            item.setSeat(seat);
            item.setPrice(showtime.getPrice());

            bookingItems.add(item);
            totalAmount += showtime.getPrice();
        }

        booking.setTotalAmount(totalAmount);
        booking.setBookingItems(bookingItems);

        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    // ─── Get Booking By ID ────────────────────────────
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    // ─── Get All Bookings By User ─────────────────────
    public List<BookingResponse> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Cancel Booking ───────────────────────────────
    @Transactional
    public BookingResponse cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + id));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    // ─── Get All Bookings (Admin) ─────────────────────
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Mapper ───────────────────────────────────────
    private BookingResponse mapToResponse(Booking booking) {
        // Get seat numbers from booking items
        List<String> seatNumbers = booking.getBookingItems()
                .stream()
                .map(item -> item.getSeat().getSeatNumber())
                .collect(Collectors.toList());

        return new BookingResponse(
                booking.getId(),
                booking.getUser().getName(),
                booking.getShowtime().getMovie().getId(),
                booking.getShowtime().getMovie().getTitle(),
                booking.getShowtime().getScreen().getTheater().getName(),
                booking.getShowtime().getStartTime(),
                booking.getBookingItems()
                        .stream()
                        .map(item -> item.getSeat().getSeatNumber())
                        .collect(Collectors.toList()),
                booking.getTotalAmount(),
                booking.getStatus().name(),
                booking.getBookingTime()
        );
    }
}