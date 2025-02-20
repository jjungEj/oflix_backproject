package com.oflix.OFlix_back.payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestPaymentDto {
    private String paymentId;
    private String resultCode;
    private String movieId;
    private String movieTitle;
    private String seats;
    private String tickets;
    private String totalAmount;
    private String paymentMethod;
}