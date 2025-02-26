package com.oflix.OFlix_back.reservations.service;

import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.repository.SeatRepository;
import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.reservations.repository.ReservationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;
    private final SeatRepository seatRepository;

    public List<Reservation> findByUserId(Long userId) {
        return reservationsRepository.findAllByUserId(userId);
    }

    public void deleteById(Long reservationId) {
        Optional<Reservation> reservation = reservationsRepository.findById(reservationId);
        if (reservation.isPresent()) {
            Reservation foundReservation = reservation.get();

            Optional<Seat> seat = seatRepository.findById(foundReservation.getSeat().getId());
            if (seat.isPresent()) {
                Seat foundSeat = seat.get();
                foundSeat.setIsAvailable(true);
                seatRepository.save(foundSeat);
            } else {
                throw new RuntimeException("해당 좌석을 찾을 수 없습니다.");
            }
            reservationsRepository.deleteById(reservationId);
        } else {
            throw new RuntimeException("해당 예약을 찾을 수 없습니다.");
        }
    }

}
