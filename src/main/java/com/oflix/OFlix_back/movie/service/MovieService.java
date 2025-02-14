package com.oflix.OFlix_back.movie.service;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
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
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new IllegalArgumentException("영화없음"));
        ResponseMovieDto responseMovieDto = new ResponseMovieDto(movie);

        return responseMovieDto;
    }

    //영화 추가
    //TODO : 조금 더 고민
    // NOTE : 반환형을 void로 해도 되지 않을까..?
    @Transactional
    public TotalResponseMovieDto createMovie(RequestMovieDto requestMovieDto, MultipartFile main, List<MultipartFile> still) {
        //1. 영화 정보 저장
        Movie movie = Movie.builder()
                .title(requestMovieDto.getTitle())
                .releaseDate(requestMovieDto.getReleaseDate())
                .director(requestMovieDto.getDirector())
                .actors(requestMovieDto.getActors())
                .synopsis(requestMovieDto.getSynopsis())
                .build();
        Movie savedMovie = movieRepository.save(movie);

        //2. 이미지 저장
        //메인포스터 경로
        String mainPath = imageService.uploadMainImage(main, savedMovie);
        //스틸컷 경로 리스트
        List<String> stillPaths = imageService.uplpadStillCuts(still, savedMovie);
        //반환 Dto 생성(영화정보랑 포스터들까지 다 합친거)
        TotalResponseMovieDto totalMovie = TotalResponseMovieDto.builder()
                .movie(savedMovie)
                .mainPosterPath(mainPath)
                .stillCutPaths(stillPaths)
                .build();

        return totalMovie;
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
    /*@Transactional
    public Page<ResponseMovieDto> searchMovies(String title, String director, String actors, Pageable pageable) {
        return movieRepository.searchMovies(title,director,actors,pageable)
                .map(ResponseMovieDto::new);
    }*/

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