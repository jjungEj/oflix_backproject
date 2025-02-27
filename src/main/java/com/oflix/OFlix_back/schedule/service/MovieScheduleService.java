package com.oflix.OFlix_back.schedule.service;

import com.oflix.OFlix_back.cinema.dto.ResponseSeatDto;
import com.oflix.OFlix_back.cinema.entity.Seat;
import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.cinema.repository.SeatRepository;
import com.oflix.OFlix_back.cinema.repository.TheaterHallRepository;
import com.oflix.OFlix_back.cinema.service.TheaterHallService;
import com.oflix.OFlix_back.image.service.ImageService;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.service.MovieService;
import com.oflix.OFlix_back.schedule.dto.MovieScheduleRequestDto;
import com.oflix.OFlix_back.schedule.dto.MovieScheduleResponseDto;
import com.oflix.OFlix_back.schedule.dto.ResponseMovieScheduleDto;
import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import com.oflix.OFlix_back.schedule.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class MovieScheduleService {
    private final MovieScheduleRepository movieScheduleRepository;
    private final SeatRepository seatRepository;
    private final ImageService imageService;
    private final TheaterHallService theaterHallService;
    private final TheaterHallRepository theaterHallRepository;
    private final MovieService movieService;

    @Transactional(readOnly = true)
    public List<MovieScheduleResponseDto> findAllSchedules() {
        List<MovieSchedule> schedules = movieScheduleRepository.findByStartTimeGreaterThanOrderByStartTimeAsc(LocalDateTime.now());

        return schedules.stream()
                .map(schedule -> {
                    MovieScheduleResponseDto dto = new MovieScheduleResponseDto();

                    String postUrl = imageService.getMainImage(schedule.getMovie().getMovieId());

                    dto.setScheduleId(schedule.getMovieScheduleId());
                    dto.setStartTime(schedule.getStartTime().toString());
                    dto.setEndTime(schedule.getEndTime().toString());
                    dto.setTitle(schedule.getMovie().getTitle());

                    dto.setPosterUrl(postUrl);
                    dto.setCinemaId(schedule.getTheaterHall().getCinema().getId());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getName());
                    dto.setCinemaLocation(schedule.getTheaterHall().getCinema().getLocation());

                    List<ResponseSeatDto> totalSeats = seatRepository.findByMovieSchedule(schedule)
                            .stream()
                            .map(Seat::toResponeSeatDto)
                            .collect(Collectors.toList());

                    List<ResponseSeatDto> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true)
                            .stream()
                            .map(Seat::toResponeSeatDto)
                            .collect(Collectors.toList());



                    dto.setTotalSeats(totalSeats.size());
                    dto.setRemainingSeats(remainingSeats);

                    return dto;
                })
                .toList();
    }

    //dto ResponseMovieScheduleDto로 수정함 바꾸지말것
    public List<ResponseMovieScheduleDto> findMovieScheduleByCinema(Long cinemaId) {
        List<MovieSchedule> schedules = movieScheduleRepository.findByCinemaId(cinemaId);

        return schedules.stream()
                .map(schedule -> {
                    ResponseMovieScheduleDto dto = new ResponseMovieScheduleDto();

                    String postUrl = imageService.getMainImage(schedule.getMovie().getMovieId());

                    dto.setScheduleId(schedule.getMovieScheduleId());
                    dto.setStartTime(schedule.getStartTime().toString());
                    dto.setEndTime(schedule.getEndTime().toString());
                    dto.setTitle(schedule.getMovie().getTitle());
                   // dto.setTheaterHall(schedule.getTheaterHall().getName());

                    dto.setPosterUrl(postUrl);
                    dto.setCinemaId(schedule.getTheaterHall().getCinema().getId());
                    dto.setCinemaName(schedule.getTheaterHall().getCinema().getName());
                    dto.setCinemaLocation(schedule.getTheaterHall().getCinema().getLocation());

                    List<ResponseSeatDto> totalSeats = seatRepository.findByMovieSchedule(schedule)
                            .stream()
                            .map(Seat::toResponeSeatDto)
                            .collect(Collectors.toList());

                    List<ResponseSeatDto> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true)
                            .stream()
                            .map(Seat::toResponeSeatDto)
                            .collect(Collectors.toList());



                    dto.setTotalSeats(totalSeats.size());
                    dto.setRemainingSeats(remainingSeats);

                    return dto;
                })
                .toList();
    }


    //스케쥴 추가
    public void createMovieSchedule(MovieScheduleRequestDto dto) {
        TheaterHall theaterHall = theaterHallRepository.findById(dto.getTheaterHallId()).orElseThrow(()->new IllegalArgumentException("상영관x"));
        //ResponseMovieDto movie = movieService.findByMovie(dto.getMovieId());
        Movie movie2 = movieService.findMovieById(dto.getMovieId());

        LocalDateTime startTime = dto.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(Integer.parseInt(movie2.getRunTime()));

        MovieSchedule schedule = new MovieSchedule();
        schedule.setTheaterHall(theaterHall);
        schedule.setMovie(movie2);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        movieScheduleRepository.save(schedule);

        createSeatsForSchedule(schedule);

    }

    //좌석 추가
    private void createSeatsForSchedule(MovieSchedule schedule) {
        TheaterHall theaterHall = schedule.getTheaterHall();
        int seatCount = 32; // 기본 100석 (상영관 크기에 따라 다르게 설정 가능)

        for (int i = 1; i <= seatCount; i++) {
            Seat seat = Seat.builder()
                    .movieSchedule(schedule)
                    .theaterHall(theaterHall)
                    .seatNumber(/*"A" + */String.valueOf(i)) // A1, A2, A3... 형태로 좌석 생성
                    .isAvailable(true)
                    .build();
            seatRepository.save(seat);
        }
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        MovieSchedule schedule = movieScheduleRepository.findById(scheduleId).orElseThrow(()-> new IllegalArgumentException("스케줄 없음"));

        ResponseMovieScheduleDto dto = new ResponseMovieScheduleDto();
        String postUrl = imageService.getMainImage(schedule.getMovie().getMovieId());

        dto.setScheduleId(schedule.getMovieScheduleId());
        dto.setStartTime(schedule.getStartTime().toString());
        dto.setEndTime(schedule.getEndTime().toString());
        dto.setTitle(schedule.getMovie().getTitle());
        dto.setTheaterHall(schedule.getTheaterHall().getName());

        dto.setPosterUrl(postUrl);
        dto.setCinemaId(schedule.getTheaterHall().getCinema().getId());
        dto.setCinemaName(schedule.getTheaterHall().getCinema().getName());
        dto.setCinemaLocation(schedule.getTheaterHall().getCinema().getLocation());

        List<ResponseSeatDto> totalSeats = seatRepository.findByMovieSchedule(schedule)
                .stream()
                .map(Seat::toResponeSeatDto)
                .collect(Collectors.toList());

        List<ResponseSeatDto> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true)
                .stream()
                .map(Seat::toResponeSeatDto)
                .collect(Collectors.toList());

        dto.setTotalSeats(totalSeats.size());
        dto.setRemainingSeats(remainingSeats);


        if (dto.getTotalSeats().equals(dto.getRemainingSeats().size())) {
            // 2. 잔여 좌석 수와 총 좌석 수가 같으면 삭제 진행
            // 관련된 좌석 삭제
            seatRepository.deleteByMovieSchedule(schedule);  // 좌석 삭제
            movieScheduleRepository.delete(schedule);  // 영화 스케줄 삭제
        } else {
            // 3. 만약 잔여 좌석 수와 총 좌석 수가 다르면 삭제 불가
            throw new RuntimeException("잔여 좌석 수가 총 좌석 수와 일치하지 않아서 삭제할 수 없습니다.");
        }

    }




    public List<MovieScheduleResponseDto> findSchedulesByTheater(Long theaterHallId) {
        List<MovieSchedule> schedules = movieScheduleRepository.findByTheaterHallId(theaterHallId);

        log.info("Found {} schedules for theaterHallId={}", schedules.size(), theaterHallId);

        return schedules.stream()
                .map(schedule -> {
                    // 사용 가능한 좌석 조회
                    List<Seat> remainingSeats = seatRepository.findByMovieScheduleAndIsAvailable(schedule, true);
                    int totalSeats = seatRepository.findByMovieSchedule(schedule).size();

                    // Seat 엔티티 -> ResponseSeatDto 변환
                    List<ResponseSeatDto> responseSeats = remainingSeats.stream()
                            .map(Seat::toResponeSeatDto)
                            .collect(Collectors.toList());

                    return new MovieScheduleResponseDto(
                            schedule.getMovieScheduleId(),
                            schedule.getStartTime().toString(),
                            schedule.getEndTime().toString(),
                            schedule.getMovie().getTitle(),
                            null,
                            totalSeats,
                            responseSeats,
                            schedule.getTheaterHall().getCinema().getId(),
                            schedule.getTheaterHall().getCinema().getName(),
                            schedule.getTheaterHall().getCinema().getLocation()
                    );
                })
                .collect(Collectors.toList());
    }

}
