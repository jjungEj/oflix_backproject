package com.oflix.OFlix_back.movie.service;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
import com.oflix.OFlix_back.movie.dto.RequestMovieDto;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public Page<ResponseMovieDto> findAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(ResponseMovieDto::new);
    }
    @Transactional
    public ResponseMovieDto findByMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            return new ResponseMovieDto(movie);
        }
        return null;
    }
    @Transactional
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    @Transactional
    public Movie updateMovie(Long id, RequestMovieDto requestMovieDto) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie != null) {
            Category category = categoryRepository.findById(requestMovieDto.getCategoryId()).orElse(null);
            Movie movie = requestMovieDto.toEntity(category);
            movie.setMovieId(id);
            return movieRepository.save(movie);
        }
        return null;
    }
    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
    @Transactional
    public Page<ResponseMovieDto> searchMovies(String title, String director, String actors, Pageable pageable) {
        return movieRepository.searchMovies(title,director,actors,pageable)
                .map(ResponseMovieDto::new);
    }


}
