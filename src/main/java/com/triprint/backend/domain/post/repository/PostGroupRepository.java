package com.triprint.backend.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.post.entity.PostGroup;

public interface PostGroupRepository extends JpaRepository<PostGroup, Long> {
}
