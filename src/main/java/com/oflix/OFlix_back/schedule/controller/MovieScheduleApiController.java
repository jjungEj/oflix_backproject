package com.oflix.OFlix_back.schedule.controller;

import com.oflix.OFlix_back.schedule.dto.MovieScheduleResponseDto;
import com.oflix.OFlix_back.schedule.service.MovieScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        log.debug("movieSchedules.toString() : {}", movieSchedules.toString());
        return ResponseEntity.ok(movieSchedules);
    }
}
