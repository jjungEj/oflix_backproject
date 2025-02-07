package com.oflix.OFlix_back.category.repository;


import com.oflix.OFlix_back.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
