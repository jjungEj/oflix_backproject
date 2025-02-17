package com.oflix.OFlix_back.schedule.dto;

import com.oflix.OFlix_back.cinema.entity.Seat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class MovieScheduleResponseDto {
    private Long scheduleId;        // 스케줄 id
    private String startTime;       // 상영 시작 시간
    private String endTime;         // 상영 종료 시간

    private String title;           // 영화 제목
//    private String posterUrl;       // 포스터 url

    private Integer totalSeats; // 잔여 석
    private List<Seat> remainingSeats; // 잔여 석
    private Long cinemaId;          // 영화관 id
    private String cinemaName;      // 영화관 이름
    private String cinemaLocation;  // 영화관 주소
}
