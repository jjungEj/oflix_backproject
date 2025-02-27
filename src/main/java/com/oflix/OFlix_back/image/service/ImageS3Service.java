package com.oflix.OFlix_back.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket; // 버킷명

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

    // 썸네일 변환 및 업로드
    /*
        1. 원본 이미지를 썸네일로 변환 (Buffered image)
        2. BuffredImage -> byte[] -> ByteArrayInputStream
        3. 메타 데이터 등 추출
        4. s3에 썸네일로 저장
        5. db에 썸네일 경로 저장 (ImageService에서)
     */

    public String converToThumbnail(MultipartFile image){
        try{
            //원본 이미지를 썸네일(BufferedImage)로 변환하고 inputStream으로 변환
            BufferedImage thumbnail = Thumbnails.of(image.getInputStream())
                    .size(200,300)
                    .asBufferedImage();

            //원래 파일명 추출
            String originalFilename = image.getOriginalFilename();
            //확장자
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //썸네일 파일명: 앞에 s_ 붙여서 썸네일 구분하기
            String s3Filename = "s_"+UUID.randomUUID().toString().substring(0,10)+originalFilename;
            //System.out.println(extension);
            byte[] thumbnailByteArray = toByteArray(thumbnail, extension);
            ByteArrayInputStream result = new ByteArrayInputStream(thumbnailByteArray);

            //ObjectMeta 데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/"+image.getContentType());
            objectMetadata.setContentLength(thumbnailByteArray.length);

            //썸네일도 s3에 저장
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3Filename, result, objectMetadata);
            amazonS3.putObject(putObjectRequest);

            //썸네일 경로 반환
            return amazonS3.getUrl(bucket, s3Filename).toString();
        }
        catch(IOException e){
            return e.getMessage();
        }
    }

    // BufferedImage -> byte[]
    public byte[] toByteArray(BufferedImage thumbnail, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbnail, format, baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }
}
