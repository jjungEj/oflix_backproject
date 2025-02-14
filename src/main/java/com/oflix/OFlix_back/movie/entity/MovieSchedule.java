package com.oflix.OFlix_back.movie.entity;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MovieSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}