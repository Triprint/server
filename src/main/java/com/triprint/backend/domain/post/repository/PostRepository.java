package com.triprint.backend.domain.post.repository;

import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByid(Long id);
}
