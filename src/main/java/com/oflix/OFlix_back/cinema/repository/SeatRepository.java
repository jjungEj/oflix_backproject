package com.oflix.OFlix_back.cinema.repository;

import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
//    List<Seat> findByTheaterHallAndIsAvailable(TheaterHall theaterHall, Boolean isAvailable);
//    List<Seat> findByTheaterHall(TheaterHall theaterHall);
}
