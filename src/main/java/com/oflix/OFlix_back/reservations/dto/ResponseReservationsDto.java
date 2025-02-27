package com.oflix.OFlix_back.reservations.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseReservationsDto {
    private Long id;
    private Long userId;
    private String seatNumber;
    private Long movieScheduleId;
    private String status;
    private Long movieId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String cinemaName;
    private String theaterHall;

    public ResponseReservationsDto(Long id, Long userId, Long movieScheduleId, String seatNumber, String status,
                                   Long movieId, String title, LocalDateTime startTime, LocalDateTime endTime, String cinemaName, String theaterHall) {
        this.id = id;
        this.userId = userId;
        this.movieScheduleId = movieScheduleId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.movieId = movieId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaName = cinemaName;
        this.theaterHall = theaterHall;
    }

}
