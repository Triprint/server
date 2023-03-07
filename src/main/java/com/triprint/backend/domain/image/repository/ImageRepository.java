package com.triprint.backend.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Image findByPath(String path);
}
