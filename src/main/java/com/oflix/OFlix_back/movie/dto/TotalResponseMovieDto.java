package com.oflix.OFlix_back.movie.dto;

import com.oflix.OFlix_back.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
// TODO : 굳이 필요한지 모르겠다 추후 삭제할수도
public class TotalResponseMovieDto {
    private Movie movie; //영화 정보
    private String mainPosterPath; //메인포스터 경로
    private List<String> stillCutPaths; //스틸컷 경로
}
