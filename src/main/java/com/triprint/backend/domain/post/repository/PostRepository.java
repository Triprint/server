package com.triprint.backend.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
