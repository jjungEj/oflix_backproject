package com.oflix.OFlix_back.payment.entity;

import com.oflix.OFlix_back.reservations.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private String paymentMethod;
    private String paymentStatus = "pending";
    private Double amount;
    private LocalDateTime paymentTime;
}