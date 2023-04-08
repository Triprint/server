package com.triprint.backend.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.comment.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
