package com.triprint.backend.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.post.entity.PostHashtag;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
}
