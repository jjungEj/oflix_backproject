package com.oflix.OFlix_back.cinema.repository;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterHallRepository extends JpaRepository<TheaterHall, Long> {
    List<TheaterHall> findByCinemaId(Long cinemaId);
}
