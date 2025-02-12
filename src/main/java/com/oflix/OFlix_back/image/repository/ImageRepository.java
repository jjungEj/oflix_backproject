package com.oflix.OFlix_back.image.repository;

import com.oflix.OFlix_back.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
