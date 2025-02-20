package com.oflix.OFlix_back.movie.repository;

import com.oflix.OFlix_back.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //영화 제목 검색
    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:keyword%")
    Page<Movie> findByTitleContaining(String keyword, Pageable pageable);

    //배우로 검색
    @Query("SELECT m FROM Movie m WHERE m.actors LIKE %:keyword%")
    Page<Movie> findByActorsContaining(String keyword, Pageable pageable);

    //감독으로 검색
    @Query("SELECT m FROM Movie m WHERE m.director LIKE %:keyword%")
    Page<Movie> findByDirectorContaining(String keyword, Pageable pageable);
}