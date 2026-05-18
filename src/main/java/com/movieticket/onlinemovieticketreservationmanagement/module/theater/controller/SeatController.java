package com.movieticket.onlinemovieticketreservationmanagement.module.theater.controller;
//seat controller implemented by IT25101119
import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.SeatResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getSeatsByShowtime(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(ApiResponse.success(seatService.getSeatsByShowtime(showtimeId)));
    }
}
