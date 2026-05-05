// module/feedback/dto/response/FeedbackResponse.java
package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long movieId;
    private String movieTitle;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}