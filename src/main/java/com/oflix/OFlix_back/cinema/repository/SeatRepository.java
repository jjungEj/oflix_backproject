package com.oflix.OFlix_back.cinema.repository;

import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByMovieScheduleAndIsAvailable(MovieSchedule movieSchedule, Boolean isAvailable);
    List<Seat> findByMovieSchedule(MovieSchedule movieSchedule);
}
