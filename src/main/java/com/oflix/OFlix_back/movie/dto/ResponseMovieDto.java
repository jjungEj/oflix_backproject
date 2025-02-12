package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.movie.entity.Movie;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMovieDto {
    private Long movieId;
    private String title;
    private String releaseDate;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;
    private String categoryName;

    public ResponseMovieDto(Movie movie) {
    }
}
