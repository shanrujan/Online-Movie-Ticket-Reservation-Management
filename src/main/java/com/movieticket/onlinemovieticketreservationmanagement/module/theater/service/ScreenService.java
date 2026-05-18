package com.movieticket.onlinemovieticketreservationmanagement.module.theater.service;

import com.movieticket.onlinemovieticketreservationmanagement.exception.BadRequestException;
import com.movieticket.onlinemovieticketreservationmanagement.exception.ResourceNotFoundException;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.request.ScreenRequest;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.dto.response.ScreenResponse;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Theater;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.ScreenRepository;
import com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public ScreenService() {
        screenRepository = null;
    }

    // Get all screens
    public List<ScreenResponse> getAllScreens() {
        return screenRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get screen by ID
    public ScreenResponse getScreenById(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with id: " + id));
        return mapToResponse(screen);
    }

    // Get screens by theater
    public List<ScreenResponse> getScreensByTheater(Long theaterId) {
        return screenRepository.findByTheaterId(theaterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create screen
    public ScreenResponse createScreen(ScreenRequest request) {
        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Theater not found with id: " + request.getTheaterId()));

        if (screenRepository.existsByNameAndTheaterId(
                request.getName(), request.getTheaterId())) {
            throw new BadRequestException(
                    "Screen already exists with name: " + request.getName());
        }

        Screen screen = Screen.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .theater(theater)
                .build();

        return mapToResponse(screenRepository.save(screen));
    }

    // Update screen
    public ScreenResponse updateScreen(Long id, ScreenRequest request) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with id: " + id));

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Theater not found with id: " + request.getTheaterId()));

        screen.setName(request.getName());
        screen.setCapacity(request.getCapacity());
        screen.setTheater(theater);

        return mapToResponse(screenRepository.save(screen));
    }

    // Delete screen
    public void deleteScreen(Long id) {
        if (!screenRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Screen not found with id: " + id);
        }
        screenRepository.deleteById(id);
    }

    // Mapper
    private ScreenResponse mapToResponse(Screen screen) {
        return new ScreenResponse(
                screen.getId(),
                screen.getName(),
                screen.getCapacity(),
                screen.getTheater().getId(),
                screen.getTheater().getName()
        );
    }
}