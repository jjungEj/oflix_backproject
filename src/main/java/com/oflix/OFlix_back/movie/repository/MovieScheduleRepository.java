package com.oflix.OFlix_back.movie.repository;

import com.oflix.OFlix_back.movie.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> { }
