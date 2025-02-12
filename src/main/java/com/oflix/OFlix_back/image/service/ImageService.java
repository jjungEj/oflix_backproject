package com.oflix.OFlix_back.image.service;

import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageS3Service imageS3Service;

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
