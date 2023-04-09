package com.triprint.backend.domain.reply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	@Query("select r from Reply r where r.post = :post")
	Page<Reply> findByReplyPost(@Param("post") Post post, Pageable page);
}
