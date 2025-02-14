package com.oflix.OFlix_back.payment.entity;

import com.oflix.OFlix_back.movie.entity.MovieSchedule;
import com.oflix.OFlix_back.user.entity.User;
import com.oflix.OFlix_back.payment.entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id")
    private MovieSchedule movieSchedule;

    @Column(length = 50)
    private String paymentMethod;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;  // 기본값 설정

    private LocalDateTime paymentTime;

    @PrePersist
    public void setPaymentTime() {
        if (paymentTime == null) {
            paymentTime = LocalDateTime.now();  // 결제 시간이 명시되지 않으면 현재 시간으로 설정
        }
    }
}
