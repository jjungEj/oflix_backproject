package com.oflix.OFlix_back.global.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //이미지 에러
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "IMAGE_NOT_FOUND", "이미지가 존재하지 않습니다."),
    //영화 에러
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND,"MOVIE_NOT_FOUND","영화가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"CATEGORY_NOT_FOUND","카테고리가 존재하지 않습니다."),
    //서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "예기치 않은 오류가 발생했습니다.");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
