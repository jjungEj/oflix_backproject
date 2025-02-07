package com.oflix.OFlix_back.category.entity;


import com.oflix.OFlix_back.category.dto.ResponseCategoryDTO;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name", length = 10, nullable = false)
    private String categoryName;

    public void update(String categoryName){
        this.categoryName = categoryName;
    }

    public ResponseCategoryDTO toResponseCategoryDTO() {
        return ResponseCategoryDTO.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
