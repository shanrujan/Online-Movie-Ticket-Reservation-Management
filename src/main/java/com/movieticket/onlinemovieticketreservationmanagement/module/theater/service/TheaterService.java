package com.movieticket.onlinemovieticketreservationmanagement.module.theater.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.TheaterRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.TheaterResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Theater;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    // Get all theaters
    public List<TheaterResponse> getAllTheaters() {
        return theaterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get theater by ID
    public TheaterResponse getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Theater not found with id: " + id));
        return mapToResponse(theater);
    }

    // Get theaters by city
    public List<TheaterResponse> getTheatersByCity(String city) {
        return theaterRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create theater
    public TheaterResponse createTheater(TheaterRequest request) {
        if (theaterRepository.existsByName(request.getName())) {
            throw new BadRequestException(
                    "Theater already exists with name: " + request.getName());
        }

        Theater theater = Theater.builder()
                .name(request.getName())
                .location(request.getLocation())
                .city(request.getCity())
                .phone(request.getPhone())
                .totalScreens(request.getTotalScreens())
                .build();

        int numScreens = request.getTotalScreens() != null && request.getTotalScreens() > 0 ? request.getTotalScreens() : 1;
        List<com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen> screens = new java.util.ArrayList<>();
        for (int i = 1; i <= numScreens; i++) {
            screens.add(com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen.builder()
                    .name("Screen " + i)
                    .capacity(60)
                    .theater(theater)
                    .build());
        }
        theater.setScreens(screens);
        theater.setTotalScreens(numScreens);

        return mapToResponse(theaterRepository.save(theater));
    }

    // Update theater
    public TheaterResponse updateTheater(Long id, TheaterRequest request) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Theater not found with id: " + id));

        theater.setName(request.getName());
        theater.setLocation(request.getLocation());
        theater.setCity(request.getCity());
        theater.setPhone(request.getPhone());
        List<com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen> screens = theater.getScreens();
        int currentActualScreens = (screens != null) ? screens.size() : 0;
        
        int newScreens = request.getTotalScreens() != null && request.getTotalScreens() > 0 ? request.getTotalScreens() : 1;
        theater.setTotalScreens(newScreens);

        if (newScreens > currentActualScreens) {
            if (screens == null) {
                screens = new java.util.ArrayList<>();
                theater.setScreens(screens);
            }
            for (int i = currentActualScreens + 1; i <= newScreens; i++) {
                screens.add(com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen.builder()
                        .name("Screen " + i)
                        .capacity(60)
                        .theater(theater)
                        .build());
            }
        }

        return mapToResponse(theaterRepository.save(theater));
    }

    // Delete theater
    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Theater not found with id: " + id);
        }
        theaterRepository.deleteById(id);
    }

    // Mapper
    private TheaterResponse mapToResponse(Theater theater) {
        return new TheaterResponse(
                theater.getId(),
                theater.getName(),
                theater.getLocation(),
                theater.getCity(),
                theater.getPhone(),
                theater.getTotalScreens() != null ? theater.getTotalScreens() : 0
        );
    }
}