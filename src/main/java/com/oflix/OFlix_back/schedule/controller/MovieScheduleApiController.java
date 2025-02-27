package com.oflix.OFlix_back.schedule.controller;

import com.oflix.OFlix_back.schedule.dto.MovieScheduleRequestDto;
import com.oflix.OFlix_back.schedule.dto.MovieScheduleResponseDto;
import com.oflix.OFlix_back.schedule.dto.ResponseMovieScheduleDto;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import com.oflix.OFlix_back.schedule.service.MovieScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MovieScheduleApiController {
    private final MovieScheduleService movieScheduleService;

    @GetMapping("/schedules")
    public ResponseEntity<?> schedules() {
        List<MovieScheduleResponseDto> movieSchedules = movieScheduleService.findAllSchedules();

        return ResponseEntity.ok(movieSchedules);
    }

    //특정 영화관의 영화 스케줄 불러오기
    @GetMapping("/schedule/{cinemaId}")
    public ResponseEntity<?> schedule(@PathVariable Long cinemaId) {
        List<ResponseMovieScheduleDto> movieSchedules = movieScheduleService.findMovieScheduleByCinema(cinemaId);
        return ResponseEntity.ok(movieSchedules);
    }

    @GetMapping("/allSchedules")
    public ResponseEntity<?> allSchedules() {
        List<MovieScheduleResponseDto> movieSchedules = movieScheduleService.findAllSchedules();
        return ResponseEntity.ok(movieSchedules);
    }

    @PostMapping("/schedules")
    public ResponseEntity<?> createSchedule(@RequestBody MovieScheduleRequestDto movieScheduleRequestDto) {
        movieScheduleService.createMovieSchedule(movieScheduleRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<String> deleteMovieSchedule(@PathVariable Long scheduleId) {
        movieScheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("영화 스케줄이 삭제되었습니다.");
    }

    @GetMapping("/schedules/theater")
    public ResponseEntity<List<MovieScheduleResponseDto>> getSchedulesByTheater(@RequestParam Long theaterHallId) {
        List<MovieScheduleResponseDto> schedules = movieScheduleService.findSchedulesByTheater(theaterHallId);
        return ResponseEntity.ok(schedules);
    }
}
