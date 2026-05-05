// module/feedback/dto/request/FeedbackRequest.java
package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;
}