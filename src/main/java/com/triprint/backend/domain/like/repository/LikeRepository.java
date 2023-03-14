package com.triprint.backend.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triprint.backend.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "select * from likes l where l.user_id = :userId  and l.post_id = :postId", nativeQuery = true)
	Optional<Like> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
