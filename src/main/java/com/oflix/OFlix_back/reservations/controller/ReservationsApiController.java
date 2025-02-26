package com.oflix.OFlix_back.reservations.controller;

import com.oflix.OFlix_back.login.dto.CustomUserDetails;
import com.oflix.OFlix_back.login.dto.UserInfoResponse;
import com.oflix.OFlix_back.login.repository.UserRepository;
import com.oflix.OFlix_back.login.service.UserService;
import com.oflix.OFlix_back.reservations.dto.ResponseReservationsDto;
import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.reservations.service.ReservationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReservationsApiController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ReservationsService reservationsService;

    @GetMapping("/myReservation")
    public ResponseEntity<?> myReservation(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseEntity<UserInfoResponse> user = userService.getUserInfo(userDetails);
        List<Reservation> reservations = reservationsService.findByUserId(
                userRepository.findByUsername(user.getBody().getUsername()).get().getId()
        );

        List<ResponseReservationsDto> reservationDtos = reservations.stream()
                .map(reservation -> new ResponseReservationsDto(
                        reservation.getId(),
                        reservation.getUser().getId(),
                        reservation.getMovieSchedule().getMovieScheduleId(),
                        reservation.getSeat().getSeatNumber(),
                        reservation.getStatus(),
                        reservation.getMovieSchedule().getMovie().getMovieId(),
                        reservation.getMovieSchedule().getMovie().getTitle(),
                        reservation.getMovieSchedule().getTheaterHall().getCinema().getName(),
                        reservation.getMovieSchedule().getTheaterHall().getName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationDtos);
    }

    @DeleteMapping("/cancelReservation/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") Long id) {
        reservationsService.deleteById(id);
        return ResponseEntity.ok("Reservation cancelled: " + id);
    }
}