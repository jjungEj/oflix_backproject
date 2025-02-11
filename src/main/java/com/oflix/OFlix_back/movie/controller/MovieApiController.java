package com.oflix.OFlix_back.movie.controller;

import com.oflix.OFlix_back.movie.dto.RequestMovieDto;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieApiController {
    private final MovieService movieService;

    @GetMapping
    public Page<ResponseMovieDto> findAllMovies(@PageableDefault(size = 10) Pageable pageable) {
        return movieService.findAllMovies(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMovieDto> findByMovie(@PathVariable Long id) {
        ResponseMovieDto movie = movieService.findByMovie(id);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody RequestMovieDto requestMovieDto) {
        Movie updatedMovie = movieService.updateMovie(id, requestMovieDto);
        if (updatedMovie != null) {
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public Page<ResponseMovieDto> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String actors,
            @PageableDefault(size = 10) Pageable pageable) {
        return movieService.searchMovies(title, director, actors, pageable);
    }
}
