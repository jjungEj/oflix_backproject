package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.movie.entity.Genre;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.entity.Nation;
import com.oflix.OFlix_back.movie.entity.ViewAge;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseMovieDto {
    private Long movieId;
    private String title;
    private String releaseDate;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;
    private String categoryName;

    private Genre genre;
    private Nation nation;
    private ViewAge viewAge;

    public ResponseMovieDto(Movie movie) {
    }
}
