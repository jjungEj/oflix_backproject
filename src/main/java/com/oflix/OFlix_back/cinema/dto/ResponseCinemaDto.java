package com.oflix.OFlix_back.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCinemaDto {
    private Long cinemaId;
    private String name;
    private String location;

}
