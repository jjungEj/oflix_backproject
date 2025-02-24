package com.oflix.OFlix_back.payment.service;

import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.repository.SeatRepository;
import com.oflix.OFlix_back.payment.dto.RequestPaymentDto;
import com.oflix.OFlix_back.payment.entity.Payment;
import com.oflix.OFlix_back.payment.repository.PaymentRepository;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import com.oflix.OFlix_back.schedule.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MovieScheduleRepository movieScheduleRepository;
    private final SeatRepository seatRepository;

    public String savePayment(RequestPaymentDto requestDto) {
        MovieSchedule movieSchedule = movieScheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + requestDto.getScheduleId()));

        if (requestDto.getTickets().isEmpty()) {
            throw new IllegalArgumentException("No tickets provided for payment.");
        }
        // 티켓 정보 저장
        for (RequestPaymentDto.Ticket ticket : requestDto.getTickets()) {
            Payment payment = new Payment();
            payment.setPaymentId(requestDto.getPaymentId());
            payment.setResultCode(requestDto.getResultCode());
            payment.setMovieSchedule(movieSchedule);
            payment.setSeatNumber(String.valueOf(ticket.getSeatIndex() + 1));
            payment.setTicketType(ticket.getTicketType().getName());
            payment.setAmount((double) ticket.getTicketType().getPrice());
            payment.setPaymentMethod(requestDto.getPaymentMethod());

            paymentRepository.save(payment);

            if(requestDto.getResultCode().equals("Success")) {
                Seat seat = seatRepository.findByMovieScheduleAndSeatNumber(movieSchedule, String.valueOf(ticket.getSeatIndex() + 1));

                seat.setIsAvailable(false);

                seatRepository.save(seat);
            }

        }

        return requestDto.getResultCode();
    }
}
