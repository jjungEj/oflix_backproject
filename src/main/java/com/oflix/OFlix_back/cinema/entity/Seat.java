package com.oflix.OFlix_back.cinema.entity;

import com.oflix.OFlix_back.cinema.dto.ResponseSeatDto;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id", nullable = false)
    private MovieSchedule movieSchedule;

    @ManyToOne
    @JoinColumn(name = "theater_hall_id", nullable = false)
    private TheaterHall theaterHall;

    private String seatNumber;
    private Boolean isAvailable;

    public ResponseSeatDto toResponeSeatDto(){
        return ResponseSeatDto.builder()
                .id(id)
                .seatNumber(seatNumber)
                .isAvailable(isAvailable)
                .build();
    }
}