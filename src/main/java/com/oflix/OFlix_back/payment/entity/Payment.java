package com.oflix.OFlix_back.payment.entity;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.reservations.entity.Reservation;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
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

    private String paymentId;

    private String resultCode;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id", nullable = false)
    private MovieSchedule movieSchedule;

    private String seatNumber;

    private String ticketType;

    private String paymentStatus = "Success";

    private String paymentMethod;

    private Double Amount;
//  TODO : 클라이언트 측에서 user 정보를 보내줘야 저장할 수 있음
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    private LocalDateTime paymentTime = LocalDateTime.now();
}