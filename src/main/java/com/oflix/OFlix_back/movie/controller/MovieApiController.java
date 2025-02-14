package com.oflix.OFlix_back.movie.controller;

import com.oflix.OFlix_back.image.dto.RequestMainPosterDto;
import com.oflix.OFlix_back.image.dto.RequestStillCutsDto;
import com.oflix.OFlix_back.movie.dto.TotalResponseMovieDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    //특정 영화 정보 조회
    @GetMapping("/{movieId}")
    public ResponseEntity<ResponseMovieDto> findByMovie(@PathVariable Long movieId) {
        ResponseMovieDto movie = movieService.findByMovie(movieId);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //영화 추가
    @PostMapping
    //이미지dto 쓰면 오류나서 MultipartFile로 변경함
    public ResponseEntity<TotalResponseMovieDto> createMovie(@RequestPart("movie") RequestMovieDto requestMovieDto,
                                                             @RequestPart("main") MultipartFile main,
                                                             @RequestPart("still") List<MultipartFile> still) {
        TotalResponseMovieDto createdMovie = movieService.createMovie(requestMovieDto, main, still);
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

    //제목 검색
    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> searchMovie(@PathVariable String title,
                                         @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto> movies = movieService.searchTitle(title,pageable);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    //배우 검색
    @GetMapping("/search/actors/{actors}")
    public ResponseEntity<?> searchActors(@PathVariable String actors,
                                          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto> movies = movieService.searchActors(actors, pageable);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    //감독 검색
    @GetMapping("/search/director/{director}")
    public ResponseEntity<?> searchDirector(@PathVariable String director,
                                            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto>movies = movieService.searchDirector(director, pageable);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}

/*
    @GetMapping("/movie/search")
    public String search(String keyword, Model model) {
        List<Movie> searchList = movieService.search(keyword);
        model.addAttribute("searchList", searchList);
        return "search";
    }
 */