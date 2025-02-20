package com.oflix.OFlix_back.cinema.controller;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.repository.CinemaRepository;
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
@RequestMapping("/api")
@RestController
public class CinemaApiController {

    private final CinemaService cinemaService;
    private final CinemaRepository cinemaRepository;

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


    @GetMapping("cinemas/search")
    public ResponseEntity<List<Cinema>> getCinemasByRegion(@RequestParam String region) {
        List<Cinema> cinemas = cinemaService.getCinemasByRegion(region);
//        if (cinemas.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
//        }
        return ResponseEntity.ok(cinemas);
    }

    // 특정 지역(region) + 특정 지점(name) 조회 (ex. 서울 강남)
    @GetMapping("cinemas/searchByName")
    public ResponseEntity<Cinema> getCinemaByName(@RequestParam String region, @RequestParam String name) {
        Cinema cinemas = cinemaService.getCinemaByName(region, name);
        if (cinemas == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("cinemas/regions")
    public ResponseEntity<List<String>> getRegions() {
        List<String> regions = cinemaService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
}
