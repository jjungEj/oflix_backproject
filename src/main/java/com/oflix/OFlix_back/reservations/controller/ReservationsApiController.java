package com.oflix.OFlix_back.reservations.controller;

import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.reservations.service.ReservationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReservationsApiController {

    private final ReservationsService reservationsService;

    @GetMapping("/ticketing")
    public ResponseEntity<Map<String, Object>> ticketing() {
        // 응답 메시지 설정
        String message = "Ticketing successful!";
        List<Reservation> reservations = reservationsService.findAll();

        // 로그에 메시지 출력 (optional)
        log.info("Ticketing request received: {}", message);
        log.info("reservationsService.findAll();: {}", reservations);

        // JSON 형태로 반환할 Map 생성
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("reservations", reservations);  // List<Reservations> 추가

        // 정상 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
