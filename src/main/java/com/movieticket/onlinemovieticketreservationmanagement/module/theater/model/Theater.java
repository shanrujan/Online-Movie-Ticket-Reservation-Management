package com.movieticket.onlinemovieticketreservationmanagement.module.theater.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "theaters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Theater name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    private String phone;

    @OneToMany(mappedBy = "theater",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Screen> screens;
}