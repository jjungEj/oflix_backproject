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

                    dto.setScheduleId(schedule.getMovieScheduleId());
                    dto.setStartTime(schedule.getStartTime().toString());
                    dto.setEndTime(schedule.getEndTime().toString());
                    dto.setTitle(schedule.getMovie().getTitle());
                    //TODO : 이미지 출력할 수 있도록 수정하기
//                    dto.setPosterUrl(schedule.getMovie().getImages().getPosterUrl());
                    dto.setCinemaId(schedule.getTheaterHall().getCinema().getId());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getName());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getLocation());

                    List<Seat> totalSeats = seatRepository.findByMovieSchedule(schedule);
                    List<Seat> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true);
                    dto.setTotalSeats(totalSeats.size());
                    dto.setRemainingSeats(remainingSeats);

                    return dto;
                })
                .toList();
    }
}
