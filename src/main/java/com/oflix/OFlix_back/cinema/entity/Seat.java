package com.oflix.OFlix_back.cinema.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_hall_id")
    private TheaterHall theaterHall;  // 여러 Seat이 하나의 TheaterHall에 속함

    @Column(length = 10, nullable = false)
    private String seatNumber;

    private Boolean isSold;
}