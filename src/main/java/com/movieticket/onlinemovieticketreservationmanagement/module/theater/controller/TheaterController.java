package com.movieticket.onlinemovieticketreservationmanagement.module.theater.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.TheaterRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.TheaterResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.service.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheaterResponse>>> getAllTheaters() {
        return ResponseEntity.ok(ApiResponse.success(theaterService.getAllTheaters()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TheaterResponse>> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(theaterService.getTheaterById(id)));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<TheaterResponse>>> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(ApiResponse.success(theaterService.getTheatersByCity(city)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TheaterResponse>> createTheater(
            @Valid @RequestBody TheaterRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Theater created successfully",
                        theaterService.createTheater(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TheaterResponse>> updateTheater(
            @PathVariable Long id,
            @Valid @RequestBody TheaterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Theater updated successfully",
                theaterService.updateTheater(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.ok(ApiResponse.success("Theater deleted successfully", null));
    }
}