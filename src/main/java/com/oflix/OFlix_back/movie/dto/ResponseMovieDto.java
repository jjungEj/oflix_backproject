package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.image.dto.ResponseImageDto;
import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.movie.entity.Genre;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.entity.Nation;
import com.oflix.OFlix_back.movie.entity.ViewAge;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseMovieDto {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;
    private String categoryName;

    private Genre genre;
    private Nation nation;
    private ViewAge viewAge;

    //순환참조 오류를 막기 위해 이미지의 dto를 받아옴
    private List<ResponseImageDto> images;

    public ResponseMovieDto(Movie movie) {
        this.movieId = movie.getMovieId();
        this.title = movie.getTitle();
        this.releaseDate = movie.getReleaseDate();
        this.director = movie.getDirector();
        this.actors = movie.getActors();
        this.synopsis = movie.getSynopsis();
        this.genre = movie.getGenre();
        this.nation = movie.getNation();
        this.viewAge = movie.getViewAge();
        this.images = movie.getImages().stream().map(ResponseImageDto::new).collect(Collectors.toList());
    }
}
