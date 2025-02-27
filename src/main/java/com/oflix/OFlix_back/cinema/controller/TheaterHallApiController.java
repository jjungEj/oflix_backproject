package com.oflix.OFlix_back.cinema.controller;

import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.cinema.service.TheaterHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TheaterHallApiController {
    private final TheaterHallService theaterHallService;

    //특정 영화관의 상영관 리스트 불러오기
    @GetMapping("/theaterHall/cinema/{cinemaId}")
    public ResponseEntity<?> getAllTheaterHallByCinemaId(@PathVariable Long cinemaId) {
        List<TheaterHall> theaterHalls = theaterHallService.getAllTheaterHallBycinema(cinemaId);

        return ResponseEntity.ok(theaterHalls);
    }
}
