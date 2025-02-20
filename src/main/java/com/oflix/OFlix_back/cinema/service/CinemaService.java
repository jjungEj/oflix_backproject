package com.oflix.OFlix_back.cinema.service;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import com.oflix.OFlix_back.cinema.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;


    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public List<Cinema> getCinemasByRegion(String region) {
        return cinemaRepository.findByRegion(region);
    }

    // 특정 지역(region) 내 특정 지점(name) 검색
    public Cinema getCinemaByName(String region, String name) {
        return cinemaRepository.findByRegionAndName(region, name)
                .stream().findFirst().orElse(null);
    }

    public List<String> getAllRegions() {
        return cinemaRepository.findDistinctRegions();
    }
}

