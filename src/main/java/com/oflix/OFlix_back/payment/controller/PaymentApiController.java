package com.oflix.OFlix_back.payment.controller;

import com.oflix.OFlix_back.payment.dto.RequestPaymentDto;
import com.oflix.OFlix_back.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PaymentApiController {
    private final PaymentService paymentService;

    @PostMapping("/payment/complete")
    public ResponseEntity<?> complete(@RequestBody RequestPaymentDto requestDto) {
        paymentService.savePayment(requestDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "complete");
        return ResponseEntity.ok(response);
    }
}

