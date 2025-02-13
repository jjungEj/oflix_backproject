package com.oflix.OFlix_back.image.service;

import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.entity.ImageType;
import com.oflix.OFlix_back.image.repository.ImageRepository;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final MovieRepository movieRepository;
    private final ImageS3Service imageS3Service;

    //특정 영화의 메인포스터만 반환
    //경로만 반환해도 되나?
    public Image getMainImage(Long movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 영화입니다."));

        Image mainPoster = imageRepository.findByMainPoster(movie, ImageType.MAIN);

        return mainPoster;
    }

    //특정 영화의 스틸컷들 반환
    public List<Image> getStillCuts(Long movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 영화입니다."));

        List<Image> stillCuts = imageRepository.findByStillCuts(movie, ImageType.STILL);

        return stillCuts;
    }

    //메인이미지랑 스틸컷 이미지 구현 로직을 나누는게 나을지도
    //메인이미지 업로드
    public String uploadMainImage(MultipartFile file, Movie movie){
        //이미지를 s3에 저장하고 경로를 반환받음
        String imagePath = imageS3Service.uploadS3Image(file);
        //반환받은 경로에서 파일명을 잘라냄
        String fileName = getFileName(imagePath);
        //이미지 객체에 저장
        Image main = Image.builder()
                .imagePath(imagePath)
                .imageName(fileName)
                .imageType(ImageType.MAIN)
                .movie(movie)
                .build();
        //DB에 저장
        imageRepository.save(main);

        return imagePath;
    }

    //스틸컷들 업로드
    public List<String> uplpadStillCuts(List<MultipartFile> files, Movie movie){
        //반복되는 파일 경로와 이름을 받을 리스트
        List<String> path = new ArrayList<>();
        List<String> filenames = new ArrayList<>();

        //for문으로 이미지를 S3에 업로드하고 리스트에 경로와 이름을 저장
        for(MultipartFile file : files){
            //s3에 이미지 업로드하고 경로, 파일명 반환
            String url = imageS3Service.uploadS3Image(file);
            String fileName = getFileName(url);
            //리스트에 추가
            path.add(url);
            filenames.add(fileName);
            log.info(url);
            log.info(fileName);
        }

        log.info(String.valueOf(path.size()));
        log.info(String.valueOf(filenames.size()));

        for(int i=0; i<path.size(); i++){
            //이미지 객체 생성
            Image stilCut = Image.builder()
                    .imagePath(path.get(i))
                    .imageName(filenames.get(i))
                    .imageType(ImageType.STILL)
                    .movie(movie)
                    .build();
            //DB에 저장
            imageRepository.save(stilCut);
        }

        return path;
    }

    //경로에서 파일명을 추출하는 메서드
    public String getFileName(String imageUrl){
        String splitStr = ".com/";
        String S3fileName = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());

        return S3fileName;
    }

    //이미지 삭제
    //이미지 테이블에서도 삭제해야하니까 이미지 아이디를 받는게 맞을듯...
    public  void deleteImage(Long imageId){
        Image image = imageRepository.findById(imageId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 이미지입니다"));

        //이미지 파일명 받아오기
        String imageName = image.getImageName();
        //s3에서 이미지 삭제
        imageS3Service.deleteS3Image(imageName);
        //이미지 테이블에서 삭제
        imageRepository.deleteById(imageId);
    }
}


    /*
    //이미지 업로드
    //여기서 파일명하고 경로를 저장함
    public String uploadImage(MultipartFile file){
        //이미지를 s3에 저장하고 경로를 반환받음
        String url = imageS3Service.uploadS3Image(file);

        //반환받은 경로에서 파일명을 잘라냄
        String fileName = getFileName(url);
        //이미지 객체에 저장
        Image image = new Image();
        //경로 저장
        image.setImagePath(url);
        //파일명 저장
        image.setImageName(fileName);
        //이미지 구분...어떻게 해야할지 모르겠음

        //DB에 저장
        //imageRepository.save(image);

        return url;
    }
    */