package com.oflix.OFlix_back.reservations.service;

import com.oflix.OFlix_back.reservations.dto.ResponseReservationsDto;
import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.reservations.repository.ReservationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;

    @Transactional
    public List<Reservation> findAll() {
        return reservationsRepository.findAll();
    }

}
