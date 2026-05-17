package com.movieticket.onlinemovieticketreservationmanagement.module.booking.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.request.BookingRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.dto.response.BookingResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

        private final BookingService bookingService;

        // Create bookings
        @PostMapping
        public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
                        @RequestBody BookingRequest request) {
                return ResponseEntity.status(201)
                                .body(ApiResponse.success(
                                                "Booking confirmed successfully",
                                                bookingService.createBooking(request)));
        }

        // Get booking by ID
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
                        @PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success(bookingService.getBookingById(id)));
        }

        // Get all bookings by user
        @GetMapping("/user/{userId}")
        public ResponseEntity<ApiResponse<List<BookingResponse>>> getUserBookings(
                        @PathVariable Long userId) {
                return ResponseEntity.ok(
                                ApiResponse.success(bookingService.getUserBookings(userId)));
        }

        // Cancel booking
        @PutMapping("/{id}/cancel")
        public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
                        @PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Booking cancelled successfully",
                                                bookingService.cancelBooking(id)));
        }

        // Get all bookings (admin only)
        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
                return ResponseEntity.ok(
                                ApiResponse.success(bookingService.getAllBookings()));
        }

        @GetMapping("/ping")
        public String ping() {
                return "Booking controller working";
        }


}
}
