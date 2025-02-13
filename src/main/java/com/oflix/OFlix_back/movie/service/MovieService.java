package com.oflix.OFlix_back.movie.service;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
import com.oflix.OFlix_back.image.dto.RequestMainPosterDto;
import com.oflix.OFlix_back.image.dto.RequestStillCutsDto;
import com.oflix.OFlix_back.image.entity.Image;
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

import java.util.List;

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
    @Transactional
    public ResponseMovieDto findByMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            return new ResponseMovieDto(movie);
        }
        return null;
    }
    @Transactional
    public TotalResponseMovieDto createMovie(RequestMovieDto requestMovieDto, RequestMainPosterDto main, RequestStillCutsDto stills) {
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
        String mainPath = imageService.uploadMainImage(main.getMainPoster(), savedMovie);
        //스틸컷 경로 리스트
        List<String> stillPaths = imageService.uplpadStillCuts(stills.getStillCuts(), savedMovie);

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
    @Transactional
    public List<Movie> search(String keyword) {
        return movieRepository.findByTitleContaining(keyword);
    }

}
