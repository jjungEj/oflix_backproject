package com.oflix.OFlix_back.movie.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
import com.oflix.OFlix_back.global.entity.exception.CustomException;
import com.oflix.OFlix_back.global.entity.exception.ErrorCode;
import com.oflix.OFlix_back.image.dto.RequestMainPosterDto;
import com.oflix.OFlix_back.image.dto.RequestStillCutsDto;
import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.entity.ImageType;
import com.oflix.OFlix_back.image.service.ImageService;
import com.oflix.OFlix_back.movie.dto.RequestMovieDto;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import com.oflix.OFlix_back.movie.dto.TotalResponseMovieDto;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Transactional
    public Page<ResponseMovieDto> findAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(ResponseMovieDto::new);
    }

    //특정 영화 조회
    @Transactional
    public ResponseMovieDto findByMovie(Long movieId) {
        //영화 정보 가져오기
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        return new ResponseMovieDto(movie);
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
                .genre(requestMovieDto.getGenre())
                .nation(requestMovieDto.getNation())
                .viewAge(requestMovieDto.getViewAge())
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

    /*
    //특정 영화 조회 이전 코드
    //영화 정보랑 이미지(포스터, 스틸컷들)를 전부 조회함
    @Transactional
    public TotalResponseMovieDto findByMovie(Long movieId) {
        //영화 정보 가져오기
        Movie movie = movieRepository.findById(movieId).orElse(null);
        /*
        if (movie != null) {
            return new ResponseMovieDto(movie);
        }

        //이미지 경로 가져오기
        //메인이미지
        String main = imageService.getMainImage(movieId);
        //스틸컷 이미지
        List<String> stillCuts = imageService.getStillCuts(movieId);
        //영화정보, 이미지(메인포스터, 스틸컷) 경로를 모두 담은 dto를 생성
        TotalResponseMovieDto responseMovie = TotalResponseMovieDto.builder()
                .movie(movie)
                .mainPosterPath(main)
                .stillCutPaths(stillCuts)
                .build();

        return responseMovie;
    }
    */