package com.oflix.OFlix_back.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket; // 버킷명

    //TODO : 다중 파일 업로드로 변경? 아니면 추가? 아니면 걍 컨트롤러에서 업로드를 for문으로 반복해버릴까
    //TODO : 받아온 이미지를 어떻게 메인과 스틸컷으로 구분하지?
    //s3에 이미지를 업로드
    public String uploadS3Image(MultipartFile file){
        // 1. 파일명 구하기
        // 원래 파일명 추출
        String originalFilename = file.getOriginalFilename();
        // 파일명 중복 방지를 위해 UUID 랜덤값을 앞에 붙여줌
        String s3Filename = UUID.randomUUID().toString().substring(0,10)+originalFilename;
        // System.out.println(s3Filename);

        try {
            // 2. putObjectRequest에 저장하기 위한 inputStream 추출하기
            InputStream inputStream = file.getInputStream();

            // 3. ObjectMetadata 설정
            // 파일 크기 구하기
            long fileLength = file.getSize();
            // 파일 확장자 구하기
            // 파일 확장자가 이미지인지 확인하는 로직을 추가해줄 수 있을 듯  lastDotIndex() 활용해보기
            String contentType = file.getContentType();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            //MIME타입으로 설정 앞에 image/ 붙여줌
            objectMetadata.setContentType("image/"+contentType);
            objectMetadata.setContentLength(fileLength);

            // 4. 버킷에 저장하기 위한 PutObjectRequest 객체 생성
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3Filename, inputStream, objectMetadata);

            // 5. s3 버킷에 이미지 업로드
            amazonS3.putObject(putObjectRequest);

            // 6. 이미지 경로 반환
            return amazonS3.getUrl(bucket, s3Filename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e); //수정하기..
        }
    }

    // 2. s3에 업로드된 이미지를 삭제
    public void deleteS3Image(String imageName) {
        // 한글 파일은 디코딩을 해줘야함!
        String decodeFile = decodeImage(imageName);

        // s3에서 이미지 삭제
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, decodeFile));
    }

    // 한글 디코딩 메서드
    public String decodeImage(String imageName) {
        try{
            String decodeFileName = URLDecoder.decode(imageName, "UTF-8");
            //System.out.println(decodeFile);
            return decodeFileName;
        }
        catch (UnsupportedEncodingException e){
            return e.getMessage();
        }
    }
}
