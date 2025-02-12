package com.oflix.OFlix_back.movie.entity;

import lombok.Getter;

@Getter
public enum ViewAge {
    ALL(0, "전체관람가"), //전체관람가
    AGE12(1, "12세관람가"), //12세 관람가
    AGE15(2, "15세관람가"), //15세 관람가
    AGE19(3, "19세관람가"); //19세 관람가

    private final int code; //영화 코드
    private final String description;

    ViewAge(int code, String description) {
        this.code = code;
        this.description = description;
    }
}