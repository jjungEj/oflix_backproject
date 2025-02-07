package com.oflix.OFlix_back.category.controller;

import com.oflix.OFlix_back.category.dto.RequestCategoryDTO;
import com.oflix.OFlix_back.category.dto.ResponseCategoryDTO;
import com.oflix.OFlix_back.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> createCategory(@RequestBody @Valid RequestCategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
