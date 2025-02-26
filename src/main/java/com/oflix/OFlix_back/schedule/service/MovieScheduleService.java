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
import java.util.stream.Collectors;

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

    public List<MovieScheduleResponseDto> findSchedulesByTheater(Long theaterHallId) {
        List<MovieSchedule> schedules = movieScheduleRepository.findByTheaterHallId(theaterHallId);

        log.info("Found {} schedules for theaterHallId={}", schedules.size(), theaterHallId);

        return schedules.stream()
                .map(schedule -> {
                    List<Seat> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true);
                    int totalSeats = seatRepository.findByMovieSchedule(schedule).size();

                    return new MovieScheduleResponseDto(
                            schedule.getMovieScheduleId(),  // ✅ 스케줄 ID
                            schedule.getStartTime().toString(),  // ✅ 시작 시간
                            schedule.getEndTime().toString(),  // ✅ 종료 시간
                            schedule.getMovie().getTitle(),  // ✅ 영화 제목
                            totalSeats,  // ✅ 총 좌석 수
                            remainingSeats,  // ✅ 남은 좌석 리스트
                            schedule.getTheaterHall().getCinema().getId(),  // ✅ 영화관 ID
                            schedule.getTheaterHall().getCinema().getName(),  // ✅ 영화관 이름
                            schedule.getTheaterHall().getCinema().getLocation()  // ✅ 영화관 주소
                    );
                })
                .collect(Collectors.toList());
    }


}
