package com.oflix.OFlix_back.movie.service;

import com.oflix.OFlix_back.category.repository.CategoryRepository;
import com.oflix.OFlix_back.global.exception.CustomException;
import com.oflix.OFlix_back.global.exception.ErrorCode;
import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.service.ImageService;
import com.oflix.OFlix_back.movie.dto.RequestMovieDto;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.entity.MovieStatus;
import com.oflix.OFlix_back.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateMovieStatuses() {
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            if (movie.getReleaseDate().isBefore(LocalDate.now())) {
                movie.setMovieStatus(MovieStatus.NOW_SHOWING); // 현재 날짜가 releaseDate 이전이면 NOW_SHOWING
            } else {
                movie.setMovieStatus(MovieStatus.COMING_SOON); // 그렇지 않으면 COMING_SOON
            }
            movieRepository.save(movie); // 상태 업데이트 후 저장
        }
    }

    @Transactional
    public Page<ResponseMovieDto> findAllMovies(Pageable pageable) {
        Page<Movie> moviesPage = movieRepository.findAll(pageable);
        log.debug("Total elements: {}", moviesPage.getTotalElements());
        return movieRepository.findAll(pageable)
                .map(ResponseMovieDto::new);
    }
    //특정 영화 조회
    @Transactional
    public ResponseMovieDto findByMovie(Long movieId) {
        //영화 정보 가져오기
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        if (movie.getReleaseDate().isBefore(LocalDate.now())) {
            movie.setMovieStatus(MovieStatus.NOW_SHOWING);
        } else {
            movie.setMovieStatus(MovieStatus.COMING_SOON);
        }

        return new ResponseMovieDto(movie); // 변경된 상태를 포함한 DTO 반환
    }

    //영화 추가
    //@Transactional
    public ResponseMovieDto createMovie(RequestMovieDto requestMovieDto, MultipartFile main, List<MultipartFile> still) {
        //1. 영화 정보 저장
        Movie movie = Movie.builder()
                .title(requestMovieDto.getTitle())
                .releaseDate(requestMovieDto.getReleaseDate())
                .director(requestMovieDto.getDirector())
                .actors(requestMovieDto.getActors())
                .synopsis(requestMovieDto.getSynopsis())
                .genre1(requestMovieDto.getGenre1())
                .genre2(requestMovieDto.getGenre2())
                .nation(requestMovieDto.getNation())
                .viewAge(requestMovieDto.getViewAge())
                .movieStatus(requestMovieDto.getMovieStatus())
                .runTime(requestMovieDto.getRunTime())
                .build();
        Movie savedMovie = movieRepository.save(movie);

        //2. 이미지 저장
        //메인포스터 경로
        imageService.uploadMainImage(main, savedMovie);

        //스틸컷 경로 리스트
        imageService.uplpadStillCuts(still, savedMovie);

        ResponseMovieDto finalMovie = new ResponseMovieDto(savedMovie);

        return finalMovie;
    }

    @Transactional
    public ResponseMovieDto updateMovie(Long movieId, RequestMovieDto requestMovieDto) {
//영화 조회
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        movie.setTitle(requestMovieDto.getTitle());
        movie.setReleaseDate(requestMovieDto.getReleaseDate());
        movie.setDirector(requestMovieDto.getDirector());
        movie.setActors(requestMovieDto.getActors());
        movie.setSynopsis(requestMovieDto.getSynopsis());
        movie.setNation(requestMovieDto.getNation());
        movie.setGenre1(requestMovieDto.getGenre1());
        movie.setGenre2(requestMovieDto.getGenre2());
        movie.setViewAge(requestMovieDto.getViewAge());


        return movieRepository.save(movie).toResponseMovieDto();
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        //영화 조회
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        //이미지 삭제
        List<Image> images = movie.getImages();
        for (Image image : images) {
            imageService.deleteImage(image.getImageId());
        }

        movieRepository.delete(movie);
    }

    //제목 검색
    @Transactional
    public Page<ResponseMovieDto> searchTitle(String keyword, Pageable pageable) {
        Page<Movie> movies = movieRepository.findByTitleContaining(keyword, pageable);
        log.debug("Found {} movies for keyword '{}'", movies.getTotalElements(), keyword);
        return movieRepository.findByTitleContaining(keyword, pageable).map(ResponseMovieDto::new);
    }
    //배우 검색
    @Transactional
    public Page<ResponseMovieDto> searchActors(String keyword, Pageable pageable) {
        return movieRepository.findByActorsContaining(keyword, pageable).map(ResponseMovieDto::new);
    }
    //감독 검색
    @Transactional
    public Page<ResponseMovieDto> searchDirector(String keyword, Pageable pageable) {
        return movieRepository.findByDirectorContaining(keyword, pageable).map(ResponseMovieDto::new);
    }
}
