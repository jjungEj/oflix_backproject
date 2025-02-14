package com.oflix.OFlix_back.category.dto;

import com.oflix.OFlix_back.category.entity.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCategoryDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }


}
