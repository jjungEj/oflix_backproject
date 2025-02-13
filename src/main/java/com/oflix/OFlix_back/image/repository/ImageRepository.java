package com.oflix.OFlix_back.image.repository;

import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.entity.ImageType;
import com.oflix.OFlix_back.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.movie=:movie AND i.imageType=:imageType") //이게맞나
    Image findByMainPoster(@Param("movie") Movie movie, @Param("imageType") ImageType imageType); //메인포스터

    @Query("SELECT i FROM Image i WHERE i.movie=:movie AND i.imageType=:imageType")
    List<Image> findByStillCuts(@Param("movie") Movie movie, @Param("imageType") ImageType imageType); //스틸컷 이미지
}
