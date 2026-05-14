package com.movieticket.onlinemovieticketreservationmanagement.module.movie.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.request.ShowtimeRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.response.ShowtimeResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Movie;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Showtime;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.MovieRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.ShowtimeRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

        private final ShowtimeRepository showtimeRepository;
        private final MovieRepository movieRepository;
        private final ScreenRepository screenRepository;

        // Get all showtimes
        public List<ShowtimeResponse> getAllShowtimes() {
                return showtimeRepository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        // Get showtime by ID
        public ShowtimeResponse getShowtimeById(Long id) {
                Showtime showtime = showtimeRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Showtime not found with id: " + id));
                return mapToResponse(showtime);
        }

        // Get showtimes by movie
        public List<ShowtimeResponse> getShowtimesByMovie(Long movieId) {
                return showtimeRepository.findByMovieId(movieId)
                                .stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        // Get upcoming showtimes
        public List<ShowtimeResponse> getUpcomingShowtimes() {
                return showtimeRepository.findByStartTimeAfter(LocalDateTime.now())
                                .stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        // Create showtime
        public ShowtimeResponse createShowtime(ShowtimeRequest request) {
                Movie movie = movieRepository.findById(request.getMovieId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Movie not found with id: " + request.getMovieId()));

                Screen screen = screenRepository.findById(request.getScreenId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Screen not found with id: " + request.getScreenId()));

                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .screen(screen)
                                .startTime(request.getStartTime())
                                .endTime(request.getEndTime())
                                .price(request.getPrice())
                                .availableSeats(request.getAvailableSeats())
                                .build();

                return mapToResponse(showtimeRepository.save(showtime));
        }

        // Update showtime
        public ShowtimeResponse updateShowtime(Long id, ShowtimeRequest request) {
                Showtime showtime = showtimeRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Showtime not found with id: " + id));

                Movie movie = movieRepository.findById(request.getMovieId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Movie not found with id: " + request.getMovieId()));

                Screen screen = screenRepository.findById(request.getScreenId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Screen not found with id: " + request.getScreenId()));

                showtime.setMovie(movie);
                showtime.setScreen(screen);
                showtime.setStartTime(request.getStartTime());
                showtime.setEndTime(request.getEndTime());
                showtime.setPrice(request.getPrice());
                showtime.setAvailableSeats(request.getAvailableSeats());

                return mapToResponse(showtimeRepository.save(showtime));
        }

        // Delete showtime
        public void deleteShowtime(Long id) {
                if (!showtimeRepository.existsById(id)) {
                        throw new ResourceNotFoundException(
                                        "Showtime not found with id: " + id);
                }
                showtimeRepository.deleteById(id);
        }

        // Mapper
        private ShowtimeResponse mapToResponse(Showtime showtime) {
                return new ShowtimeResponse(
                                showtime.getId(),
                                showtime.getMovie().getId(),
                                showtime.getMovie().getTitle(),
                                showtime.getScreen().getId(),
                                showtime.getScreen().getName(),
                                showtime.getScreen().getTheater().getName(),
                                showtime.getStartTime(),
                                showtime.getEndTime(),
                                showtime.getPrice(),
                                showtime.getAvailableSeats());
        }
}