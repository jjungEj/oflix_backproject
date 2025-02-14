package com.oflix.OFlix_back.cinema.controller;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // React CORS 설정
@RequestMapping("/api")
@RestController
public class CinemaApiController {

    private final CinemaService cinemaService;

    /**
     * 모든 극장 목록 조회
     */
    @GetMapping("/cinemas")
    public ResponseEntity<Map<String, Object>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.findAll();

        // 로그 출력
        log.info("Retrieved {} cinemas", cinemas.size());

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("count", cinemas.size());  // 극장 개수 추가
        response.put("cinemas", cinemas);

        return ResponseEntity.ok(response);
    }

    /**
     * 특정 극장 상세 조회
     */
    @GetMapping("/cinemas/{id}")
    public ResponseEntity<Object> getCinemaById(@PathVariable Long id) {
        Optional<Cinema> cinema = cinemaService.findById(id);

        if (cinema.isPresent()) {
            log.info("Cinema found: {}", cinema.get());
            return ResponseEntity.ok(cinema.get());
        } else {
            log.warn("Cinema with id {} not found", id);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Cinema not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
