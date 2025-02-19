package com.oflix.OFlix_back.movie.entity;

import lombok.Getter;

@Getter
public enum MovieStatus {
    NOW_SHOWING(0, "현재상영작"),
    COMING_SOON(1, "상영예정작");

    private final int code;
    private final String description;

    MovieStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
