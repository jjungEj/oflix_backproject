package com.oflix.OFlix_back.category.service;


import com.oflix.OFlix_back.category.dto.RequestCategoryDTO;
import com.oflix.OFlix_back.category.dto.ResponseCategoryDTO;
import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO) {
        Category newCategory = requestCategoryDTO.toEntity();
        return categoryRepository.save(newCategory).toResponseCategoryDTO();
    }

    @Transactional(readOnly = true)
    public List<ResponseCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::toResponseCategoryDTO).toList();
    }

    @Transactional
    public ResponseCategoryDTO updateCategory(Long categoryId, RequestCategoryDTO requestCategoryDTO) {
        //원래 카테고리 불러오기
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new IllegalArgumentException("해당 카테고리가 없습니다."));
        //카테고리 수정하기
        category.update(requestCategoryDTO.getCategoryName());
        //카테고리 다시 저장
        return categoryRepository.save(category).toResponseCategoryDTO();
    }

    //delete
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
