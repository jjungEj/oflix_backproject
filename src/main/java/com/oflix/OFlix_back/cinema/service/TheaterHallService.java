package com.oflix.OFlix_back.cinema.service;

import com.oflix.OFlix_back.cinema.entity.TheaterHall;
import com.oflix.OFlix_back.cinema.repository.TheaterHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterHallService {
    private final TheaterHallRepository theaterHallRepository;

    //특정 영화관 id의 상영관 조회
    public List<TheaterHall> getAllTheaterHallBycinema(Long cinemaId){
        return theaterHallRepository.findByCinemaId(cinemaId);
    }


}
