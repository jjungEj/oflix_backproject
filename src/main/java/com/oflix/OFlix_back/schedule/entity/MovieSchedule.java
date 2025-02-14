package com.oflix.OFlix_back.schedule.entity;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_hall_id", nullable = false)
    private TheaterHall theaterHall;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private String userName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp startTime;
    private Timestamp endTime;
}