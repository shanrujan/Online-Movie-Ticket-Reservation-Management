package com.movieticket.onlinemovieticketreservationmanagement.module.user.repository;

import com.movieticket.onlinemovieticketreservationmanagement.module.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // 3. Helper checks for Registration (to make sure no duplicates exist)
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}