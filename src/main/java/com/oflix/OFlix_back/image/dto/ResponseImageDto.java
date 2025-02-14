package com.oflix.OFlix_back.image.dto;

import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.entity.ImageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class ResponseImageDto {
    private Long imageId;

    private ImageType imageType; //이미지 구분 (포스터인지 스틸컷인지)

    private String imageName; //이미지 이름

    private String imagePath; //이미지 경로

    //순환참조 오류를 막기 위해 movie의 id만 받아옴
    private Long movieId;

    public ResponseImageDto(Image image) {
        this.imageId = image.getImageId();
        this.imageType = image.getImageType();
        this.imageName = image.getImageName();
        this.imagePath = image.getImagePath();
        this.movieId = image.getMovie().getMovieId();
    }
}
