// MovieService.java
package com.movieticket.onlinemovieticketreservationmanagement.module.movie.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.request.MovieRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.dto.response.MovieResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.Movie;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.model.MovieStatus;
import com.movieticket.onlinemovieticketreservationmanagement.module.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    // Get all movies
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get movie by ID
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Movie not found with id: " + id));
        return mapToResponse(movie);
    }

    // Get now showing
    public List<MovieResponse> getNowShowing() {
        return movieRepository.findByStatus(MovieStatus.NOW_SHOWING)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Search by title
    public List<MovieResponse> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create movie
    public MovieResponse createMovie(MovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .description(request.getDescription())
                .posterUrl(request.getPosterUrl())
                .releaseDate(request.getReleaseDate())
                .status(request.getStatus())
                .build();
        return mapToResponse(movieRepository.save(movie));
    }

    // Update movie
    public MovieResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Movie not found with id: " + id));

        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setDescription(request.getDescription());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setStatus(request.getStatus());

        return mapToResponse(movieRepository.save(movie));
    }

    // Delete movie
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    // Mapper
    private MovieResponse mapToResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getLanguage(),
                movie.getDuration(),
                movie.getDescription(),
                movie.getPosterUrl(),
                movie.getReleaseDate(),
                movie.getStatus().name()
        );
    }
}