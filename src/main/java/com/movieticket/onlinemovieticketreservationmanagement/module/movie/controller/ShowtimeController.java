package com.movieticket.onlinemovieticketreservationmanagement.module.movie.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.request.ShowtimeRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.response.ShowtimeResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getAllShowtimes() {
        return ResponseEntity.ok(ApiResponse.success(showtimeService.getAllShowtimes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowtimeResponse>> getShowtimeById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(showtimeService.getShowtimeById(id)));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getByMovie(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(ApiResponse.success(
                showtimeService.getShowtimesByMovie(movieId)));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getUpcoming() {
        return ResponseEntity.ok(ApiResponse.success(
                showtimeService.getUpcomingShowtimes()));
    }

    // ✅ FIXED: removed @PreAuthorize
    @PostMapping
    public ResponseEntity<ApiResponse<ShowtimeResponse>> createShowtime(
            @Valid @RequestBody ShowtimeRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Showtime created successfully",
                        showtimeService.createShowtime(request)));
    }

    // ✅ FIXED
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowtimeResponse>> updateShowtime(
            @PathVariable Long id,
            @Valid @RequestBody ShowtimeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Showtime updated successfully",
                showtimeService.updateShowtime(id, request)));
    }

    // ✅ FIXED
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok(ApiResponse.success("Showtime deleted successfully", null));
    }
}