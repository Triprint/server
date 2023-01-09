package com.triprint.backend.domain.like.repository;

import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("select l from Like l where l.user = :user  and l.post = :post")
	Optional<Like> findByUserAndPost(@Param("user") User user,@Param("post") Post post);

}
