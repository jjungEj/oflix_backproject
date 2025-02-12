package com.oflix.OFlix_back.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMainPosterDto {
    MultipartFile mainPoster; //메인포스터
}
