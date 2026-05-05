package com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheaterId(Long theaterId);
    boolean existsByNameAndTheaterId(String name, Long theaterId);
}