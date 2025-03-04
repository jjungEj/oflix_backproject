package com.oflix.OFlix_back.schedule.repository;

import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
    List<MovieSchedule> findByStartTimeGreaterThanOrderByStartTimeAsc(LocalDateTime currentTime);
    boolean existsByMovie(Movie movie);
    @Query("SELECT m FROM MovieSchedule m WHERE m.theaterHall.id = :theaterHallId")
    List<MovieSchedule> findByTheaterHallId(@Param("theaterHallId") Long theaterHallId);

    @Query("SELECT ms FROM MovieSchedule ms WHERE ms.theaterHall.cinema.id = :cinemaId")
    List<MovieSchedule> findByCinemaId(@Param("cinemaId") Long cinemaId);

}