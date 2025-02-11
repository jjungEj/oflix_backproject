package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.movie.entity.Movie;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMovieDto {
    private String title;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;

    public Movie toEntity(Category category) {
        return Movie.builder()
                .build();
    }
}
