package com.oflix.OFlix_back.movie.repository;

import com.oflix.OFlix_back.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE " +
            "(:title is null or m.title LIKE %:title%) AND " +
            "(:director is null or m.director LIKE %:director%) AND " +
            "(:actors is null or m.actors LIKE %:actors%)")
    Page<Movie> searchMovies(@Param("title") String title,
                             @Param("director") String director,
                             @Param("actors") String actors,
                             Pageable pageable);
}
