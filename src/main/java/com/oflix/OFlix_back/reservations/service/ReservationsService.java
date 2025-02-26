package com.oflix.OFlix_back.reservations.service;

import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.reservations.repository.ReservationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;

    public List<Reservation> findByUserId(Long userId) {
        return reservationsRepository.findAllByUserId(userId);
    }
}
