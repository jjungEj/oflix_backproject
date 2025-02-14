package com.oflix.OFlix_back.movie.entity;

import lombok.Getter;

@Getter
public enum Genre {
    FANTASY(0, "판타지"), //판타지
    ACTION(1, "액션"), //액션
    SF(2, "SF"), //SF
    CRIME(3, "범죄"), //범죄
    COMEDY(4, "코미디"), //코미디
    DRAMA(5, "드라마"), //드라마
    HORROR(6, "공포"), //공포
    THRILLER(7, "스릴러"), //스릴러
    FAMILY(8, "가족"), //가족
    MYSTERY(9, "미스터리"), //미스터리
    ROMANCE(10, "로맨스"), //로맨스
    MUSIC(11, "음악"); //음악

    private final int code; //영화 코드
    private final String description;

    Genre(int code, String description) {
        this.code = code;
        this.description = description;
    }
}