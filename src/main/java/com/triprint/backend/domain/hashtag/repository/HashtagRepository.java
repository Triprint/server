package com.triprint.backend.domain.hashtag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.hashtag.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	Optional<Hashtag> findByContents(String hashtag);
}
