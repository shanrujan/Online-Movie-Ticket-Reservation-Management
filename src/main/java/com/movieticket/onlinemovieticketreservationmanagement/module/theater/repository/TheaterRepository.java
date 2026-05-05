package com.movieticket.onlinemovieticketreservationmanagement.module.theater.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.theater.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findByCity(String city);
    List<Theater> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}