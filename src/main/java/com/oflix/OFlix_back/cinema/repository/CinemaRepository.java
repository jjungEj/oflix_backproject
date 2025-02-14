package com.oflix.OFlix_back.cinema.repository;


import com.oflix.OFlix_back.cinema.entity.Cinema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
