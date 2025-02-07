package com.oflix.OFlix_back.reservations.repository;

import com.oflix.OFlix_back.reservations.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
}
