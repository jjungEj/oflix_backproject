package com.oflix.OFlix_back.cinema.controller;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // React app의 URL
@RequestMapping("/api")
@RestController
public class CinemaApiController {
    private final CinemaService cinemaService;

    @GetMapping("/cinemas")
    public ResponseEntity<Map<String, Object>> cinemas() {
        // 응답 메시지 설정
        List<Cinema> cinemas = cinemaService.findAll();

        // 로그에 메시지 출력 (optional)
        log.info("cinemaService.findAll();: {}", cinemas);

        // JSON 형태로 반환할 Map 생성
        Map<String, Object> response = new HashMap<>();
        response.put("cinemas", cinemas);

        // 정상 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
