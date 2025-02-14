package com.oflix.OFlix_back.schedule.service;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.repository.CinemaRepository;
import com.oflix.OFlix_back.cinema.repository.SeatRepository;
import com.oflix.OFlix_back.schedule.dto.MovieScheduleResponseDto;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import com.oflix.OFlix_back.schedule.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieScheduleService {
    private final CinemaRepository cinemaRepository;
    private final MovieScheduleRepository movieScheduleRepository;
    private final SeatRepository seatRepository;

    public List<MovieScheduleResponseDto> findAllSchedules() {
        List<MovieSchedule> schedules = movieScheduleRepository.findByStartTimeGreaterThanOrderByStartTimeAsc(LocalDateTime.now());

        return schedules.stream()
                .map(schedule -> {
                    MovieScheduleResponseDto dto = new MovieScheduleResponseDto();

                    dto.setScheduleId(schedule.getId());
                    dto.setStartTime(schedule.getStartTime().toString());
                    dto.setEndTime(schedule.getEndTime().toString());
                    dto.setTitle(schedule.getMovie().getTitle());
                    dto.setPosterUrl(schedule.getMovie().getPosterUrl());
                    dto.setCinemaId(schedule.getTheaterHall().getCinema().getId());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getName());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getLocation());
//
//                    List<Seat> seats = seatRepository.findByTheaterHall(schedule.getTheaterHall());
//                    dto.setRemainingSeats(seats.size());
//
//                    Cinema cinema = schedule.getTheaterHall().getCinema();
//                    dto.setCinemaId(cinema.getId());
//                    dto.setCinemaName(cinema.getName());
//                    dto.setCinemaLocation(cinema.getLocation());
                    System.out.println(dto.toString());
                    System.out.println(dto);
                    return dto;
                })
                .toList();
    }
}
