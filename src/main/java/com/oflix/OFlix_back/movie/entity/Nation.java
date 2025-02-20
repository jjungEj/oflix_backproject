package com.oflix.OFlix_back.movie.entity;

import lombok.Getter;

@Getter
public enum Nation {
    KOREA(0, "한국"), //한국
    USA(1, "미국"), //미국
    FOREIGN(2, "해외"); //그 외 국가들..

    final private int code; //영화 코드
    final private String description; //설명

    Nation(int code, String description) {
        this.code = code;
        this.description = description;
    }
}