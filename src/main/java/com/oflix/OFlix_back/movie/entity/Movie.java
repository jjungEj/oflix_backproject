package com.oflix.OFlix_back.movie.entity;

import jakarta.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    // getters and setters
}