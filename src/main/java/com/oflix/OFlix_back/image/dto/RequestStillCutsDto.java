package com.oflix.OFlix_back.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestStillCutsDto {
    List<MultipartFile> stillCuts; //스틸컷들
}
