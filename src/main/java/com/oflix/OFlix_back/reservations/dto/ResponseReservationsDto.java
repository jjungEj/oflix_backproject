package com.oflix.OFlix_back.reservations.dto;

import lombok.*;

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
    private String title;
    private String cinemaName;
    private String theaterHall;

    public ResponseReservationsDto(Long userId, Long movieScheduleId, String seatNumber, String status,
                                   Long movieId, String title, String cinemaName, String theaterHall) {
        this.userId = userId;
        this.movieScheduleId = movieScheduleId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.movieId = movieId;
        this.title = title;
        this.cinemaName = cinemaName;
        this.theaterHall = theaterHall;
    }

}
