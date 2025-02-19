package com.oflix.OFlix_back.movie.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.oflix.OFlix_back.movie.dto.RequestMovieDto;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieApiController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public Page<ResponseMovieDto> findAllMovies(
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return movieService.findAllMovies(sortedPageable);
    }

    //특정 영화 정보 조회
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<ResponseMovieDto> findByMovie(@PathVariable Long movieId) {
        ResponseMovieDto movie = movieService.findByMovie(movieId);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //영화 추가
    @PostMapping("/movies")
    //이미지dto 쓰면 오류나서 MultipartFile로 변경함
    public ResponseEntity<ResponseMovieDto> createMovie(@RequestPart("movie") RequestMovieDto requestMovieDto,
                                                             @RequestPart("main") MultipartFile main,
                                                             @RequestPart("still") List<MultipartFile> still) {
        ResponseMovieDto createdMovie = movieService.createMovie(requestMovieDto, main, still);

        System.out.println("영화 "+createdMovie);

        return ResponseEntity.ok(createdMovie);
    }


    @PutMapping("/movies/{id}")
    public ResponseEntity<ResponseMovieDto> updateMovie(@PathVariable Long id, @RequestBody RequestMovieDto requestMovieDto) {
        ResponseMovieDto updatedMovie = movieService.updateMovie(id, requestMovieDto);
        if (updatedMovie != null) {
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //제목 검색
    @GetMapping("/movies/search/title/{title}")
    public ResponseEntity<?> searchMovie(@PathVariable String title,
                                         @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if (title == null || title.isEmpty()) {
            return new ResponseEntity<>("Title is required", HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto> movies = movieService.searchTitle(title,pageable);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    //배우 검색
    @GetMapping("/movies/search/actors/{actors}")
    public ResponseEntity<?> searchActors(@PathVariable String actors,
                                          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto> movies = movieService.searchActors(actors, pageable);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    //감독 검색
    @GetMapping("/movies/search/director/{director}")
    public ResponseEntity<?> searchDirector(@PathVariable String director,
                                            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseMovieDto>movies = movieService.searchDirector(director, pageable);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
