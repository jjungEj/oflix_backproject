package com.oflix.OFlix_back.payment.service;

import com.oflix.OFlix_back.payment.dto.RequestPaymentDto;
import com.oflix.OFlix_back.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public String savePayment(RequestPaymentDto requestDto) {
//        paymentRepository.findAll();
      return "";
    }
}
