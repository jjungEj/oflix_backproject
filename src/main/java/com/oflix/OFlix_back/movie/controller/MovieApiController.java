package com.oflix.OFlix_back.movie.controller;

import com.oflix.OFlix_back.image.dto.RequestMainPosterDto;
import com.oflix.OFlix_back.image.dto.RequestStillCutsDto;
import com.oflix.OFlix_back.movie.dto.TotalResponseMovieDto;
import org.springframework.ui.Model;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    //영화 추가
    @PostMapping
    public ResponseEntity<TotalResponseMovieDto> createMovie(@RequestBody RequestMovieDto requestMovieDto, @RequestPart RequestMainPosterDto mainPosterDto, @RequestPart RequestStillCutsDto stillCutsDto) {
        TotalResponseMovieDto createdMovie = movieService.createMovie(requestMovieDto, mainPosterDto, stillCutsDto);
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


    @GetMapping("/movie/search")
    public String search(String keyword, Model model) {
        List<Movie> searchList = movieService.search(keyword);
        model.addAttribute("searchList", searchList);
        return "search";
    }
}
