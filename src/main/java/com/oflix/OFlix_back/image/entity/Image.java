package com.oflix.OFlix_back.image.entity;

import com.oflix.OFlix_back.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private ImageType imageType; //이미지 구분 (포스터인지 스틸컷인지) enum으로 바꾸는 게 나을지도

    private String imageName; //이미지 이름

    private String imagePath; //이미지 경로

    //연관관계 설정
    @ManyToOne
    @JoinColumn(name="movie_id") //movieId?
    private Movie movie;



}
