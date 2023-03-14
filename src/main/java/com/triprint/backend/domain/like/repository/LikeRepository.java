package com.triprint.backend.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triprint.backend.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("select l from Like l where l.user_id = :user_id  and l.post_id = :post_id")
	Optional<Like> getUserIdAndPostId(@Param("user_id") Long userId, @Param("post_id") Long postId);
}
