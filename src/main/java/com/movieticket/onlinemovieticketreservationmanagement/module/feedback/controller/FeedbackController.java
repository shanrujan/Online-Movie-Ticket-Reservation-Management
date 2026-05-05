package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.controller;

import com.movieticket.onlinemovieticketreservationmanagement.dto.response.ApiResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.request.FeedbackRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.response.FeedbackResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackResponse>> addFeedback(
            @Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Feedback submitted successfully",
                        feedbackService.addFeedback(request)));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getByMovie(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackService.getFeedbackByMovie(movieId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackService.getFeedbackByUser(userId)));
    }

    @GetMapping("/movie/{movieId}/average-rating")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackService.getAverageRating(movieId)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok(ApiResponse.success("Feedback deleted", null));
    }
}