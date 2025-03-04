package com.oflix.OFlix_back.image.service;

import com.oflix.OFlix_back.global.exception.CustomException;
import com.oflix.OFlix_back.global.exception.ErrorCode;
import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.entity.ImageType;
import com.oflix.OFlix_back.image.repository.ImageRepository;
import com.oflix.OFlix_back.movie.entity.Movie;
import com.oflix.OFlix_back.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final MovieRepository movieRepository;
    private final ImageS3Service imageS3Service;

    //특정 영화의 메인포스터만 반환
    //TODO : 경로만 반환해야할지, 이미지 객체를 반환해야할지?
    // NOTE : 일단 경로만 반환하게 하고 추후 필요에 따라 수정하기
    public String getMainImage(Long movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        String mainPoster = imageRepository.findByMainPoster(movie, ImageType.MAIN);

        return mainPoster;
    }

    //특정 영화의 스틸컷들 반환
    public List<String> getStillCuts(Long movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        List<String> stillCuts = imageRepository.findByStillCuts(movie, ImageType.STILL);

        return stillCuts;
    }

    //메인이미지 업로드
    public void uploadMainImage(MultipartFile file, Movie movie){
        //이미지를 s3에 저장하고 경로를 반환받음
        String imagePath = imageS3Service.uploadS3Image(file);
        //반환받은 경로에서 파일명을 잘라냄
        String fileName = getFileName(imagePath);
        //썸네일 경로..
        String thumbnailPath = imageS3Service.converToThumbnail(file);

        //이미지 객체에 저장
        Image main = Image.builder()
                .imagePath(imagePath)
                .imageName(fileName)
                .imageType(ImageType.MAIN)
                .thumbnailPath(thumbnailPath)
                .movie(movie)
                .build();

        //movie에 이미지 저장..
        String image = movie.addImage(main);

        //DB에 저장
        imageRepository.save(main);

        System.out.println(image);

        //  return imagePath;
    }

    //스틸컷 업로드
    public void uplpadStillCuts(List<MultipartFile> files, Movie movie){
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

            //이미지를 movie에 저장하는 로직 추가하기
            movie.addImage(stilCut);
        }

        // return path;
    }

    //배너이미지
    public String uploadBanner(MultipartFile file, /*Movie movie*/ Long movieId){
        //이미지를 s3에 저장하고 경로를 반환받음
        String imagePath = imageS3Service.uploadS3Image(file);
        //반환받은 경로에서 파일명을 잘라냄
        String fileName = getFileName(imagePath);

        Movie movie = movieRepository.findById(movieId).orElseThrow();

        //이미지 객체에 저장
        Image banner = Image.builder()
                .imagePath(imagePath)
                .imageName(fileName)
                .imageType(ImageType.BANNER)
                .movie(movie)
                .build();

        //movie에 이미지 저장..
        String image = movie.addImage(banner);

        //DB에 저장
        imageRepository.save(banner);

        return banner.getImagePath();
    }


    //경로에서 파일명을 추출하는 메서드
    public String getFileName(String imageUrl){
        String splitStr = ".com/";
        String S3fileName = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());

        return S3fileName;
    }

    //이미지 삭제
    //이미지 테이블에서도 삭제해야하니까 이미지 아이디를 받는게 맞을듯...
    public void deleteImage(Long imageId){
        Image image = imageRepository.findById(imageId).orElseThrow(()->new CustomException(ErrorCode.IMAGE_NOT_FOUND));

        //이미지 파일명 받아오기
        String imageName = image.getImageName();
        //s3에서 이미지 삭제
        imageS3Service.deleteS3Image(imageName);
        //이미지 테이블에서 삭제
        imageRepository.deleteById(imageId);
    }
}

    /* 사용안함
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
