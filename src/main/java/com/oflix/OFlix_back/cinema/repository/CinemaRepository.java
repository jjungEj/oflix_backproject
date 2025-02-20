package com.oflix.OFlix_back.cinema.repository;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByLocationContaining(String location); // 특정 지역 기반으로 검색 가능

    // 특정 지역(region)으로 검색
    List<Cinema> findByRegion(String region);

    // 특정 지역(region) + 특정 지점명(name)으로 검색
    List<Cinema> findByRegionAndName(String region, String name);

    @Query("SELECT DISTINCT c.region FROM Cinema c")
    List<String> findDistinctRegions();
}

