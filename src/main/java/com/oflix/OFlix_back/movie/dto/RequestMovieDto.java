package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.movie.entity.Genre;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.entity.Nation;
import com.oflix.OFlix_back.movie.entity.ViewAge;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMovieDto {
    private String title;
    private String releaseDate;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;

    private Genre genre;
    private Nation nation;
    private ViewAge viewAge;


    public Movie toEntity(Category category) {
        return Movie.builder()
                .build();
    }
}
