package com.oflix.OFlix_back.cinema.dto;

import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseSeatDto {
    private Long id;
    private String theaterHallId;
    private String seatNumber;
    private Boolean isAvailable;
}
