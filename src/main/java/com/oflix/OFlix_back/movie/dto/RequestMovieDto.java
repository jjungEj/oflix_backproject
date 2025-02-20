package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.movie.entity.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMovieDto {
    private String title;
    private LocalDate releaseDate;
    private String director;
    private String actors;
    private String synopsis;
    private Long categoryId;

    private Genre genre;
    private Nation nation;
    private ViewAge viewAge;
    private MovieStatus movieStatus;


    public Movie toEntity(Category category) {
        return Movie.builder()
                .build();
    }
}
