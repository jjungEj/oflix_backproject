package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TotalResponseMovieDto {
    private Movie movie; //영화 정보
    private String mainPosterPath; //메인포스터 경로
    private List<String> stillCutPaths; //스틸컷 경로
}
