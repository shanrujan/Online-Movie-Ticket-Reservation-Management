// module/feedback/service/FeedbackService.java
package com.movieticket.onlinemovieticketreservationmanagement.module.feedback.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.request.FeedbackRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.dto.response.FeedbackResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.model.Feedback;
import com.movieticket.onlinemovieticketreservationmanagement.module.feedback.repository.FeedbackRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Movie;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.MovieRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.model.Booking;
import com.movieticket.onlinemovieticketreservationmanagement.module.booking.repository.BookingRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import com.movieticket.onlinemovieticketreservationmanagement.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final BookingRepository bookingRepository;

    // Add feedback
    public FeedbackResponse addFeedback(FeedbackRequest request) {
        // Check if feedback already exists for this booking
        if (feedbackRepository.existsByBookingId(request.getBookingId())) {
            throw new BadRequestException(
                    "You have already submitted feedback for this booking");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
                
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setBooking(booking);
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        feedback.setCreatedAt(LocalDateTime.now());

        Feedback saved = feedbackRepository.save(feedback);
        return mapToResponse(saved);
    }

    // Get all feedback
    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all feedback for a movie
    public List<FeedbackResponse> getFeedbackByMovie(Long movieId) {
        return feedbackRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all feedback by a user
    public List<FeedbackResponse> getFeedbackByUser(Long userId) {
        return feedbackRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get average rating for a movie
    public double getAverageRating(Long movieId) {
        List<Feedback> feedbacks = feedbackRepository.findByMovieId(movieId);
        if (feedbacks.isEmpty()) return 0.0;
        return feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
    }

    // Delete feedback
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found");
        }
        feedbackRepository.deleteById(id);
    }

    // Mapper
    private FeedbackResponse mapToResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getUser().getId(),
                feedback.getUser().getFullName(),
                feedback.getMovie().getId(),
                feedback.getMovie().getTitle(),
                feedback.getBooking() != null ? feedback.getBooking().getId() : null,
                feedback.getRating(),
                feedback.getComment(),
                feedback.getCreatedAt()
        );
    }
}