package com.oflix.OFlix_back.image.entity;

import lombok.Getter;

@Getter
public enum ImageType {
    MAIN(0), //메인 이미지
    STILL(1), //스틸컷
    BANNER(2); //메인이미지 배너

    private int code;

    ImageType(int code) {
        this.code = code;
    }
}
