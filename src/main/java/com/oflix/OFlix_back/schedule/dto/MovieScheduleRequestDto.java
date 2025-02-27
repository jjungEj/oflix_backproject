package com.oflix.OFlix_back.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieScheduleRequestDto {
    private Long theaterHallId;
    private Long movieId;
    private LocalDateTime startTime;
}
