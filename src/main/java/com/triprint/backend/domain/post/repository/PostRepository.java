package com.triprint.backend.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p join p.likes l where l.user = :user")
	Page<Post> findByLikeUser(@Param("user") User user, Pageable page);
}
