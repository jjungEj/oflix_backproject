package com.oflix.OFlix_back.category.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ResponseCategoryDto {

    private Long categoryId;
    private String categoryName;
}
