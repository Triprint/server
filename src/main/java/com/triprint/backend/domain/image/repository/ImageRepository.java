package com.triprint.backend.domain.image.repository;

import com.triprint.backend.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByPath(String path);
}
