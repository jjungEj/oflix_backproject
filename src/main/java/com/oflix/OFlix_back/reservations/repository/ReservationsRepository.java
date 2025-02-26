package com.oflix.OFlix_back.reservations.repository;

import com.oflix.OFlix_back.reservations.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(Long userId);
}
