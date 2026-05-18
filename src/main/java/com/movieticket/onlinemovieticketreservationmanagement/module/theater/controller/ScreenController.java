package com.movieticket.onlinemovieticketreservationmanagement.module.theater.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.ScreenRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.SeatRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.ScreenResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.SeatResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.service.ScreenService;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;
    private final SeatService seatService;

    public ScreenController() {
        screenService = null;
        seatService = null;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ScreenResponse>>> getAllScreens() {
        return ResponseEntity.ok(ApiResponse.success(screenService.getAllScreens()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenResponse>> getScreenById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(screenService.getScreenById(id)));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponse<List<ScreenResponse>>> getByTheater(@PathVariable Long theaterId) {
        return ResponseEntity.ok(ApiResponse.success(screenService.getScreensByTheater(theaterId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ScreenResponse>> createScreen(
            @Valid @RequestBody ScreenRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Screen created successfully",
                        screenService.createScreen(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenResponse>> updateScreen(
            @PathVariable Long id,
            @Valid @RequestBody ScreenRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Screen updated successfully",
                screenService.updateScreen(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok(ApiResponse.success("Screen deleted successfully", null));
    }

    @GetMapping("/{screenId}/seats")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getSeatsByScreen(@PathVariable Long screenId) {
        return ResponseEntity.ok(ApiResponse.success(seatService.getSeatsByScreen(screenId)));
    }

    @PostMapping("/seats")
    public ResponseEntity<ApiResponse<SeatResponse>> createSeat(
            @Valid @RequestBody SeatRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Seat created successfully",
                        seatService.createSeat(request)));
    }

    @DeleteMapping("/seats/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok(ApiResponse.success("Seat deleted successfully", null));
    }
}