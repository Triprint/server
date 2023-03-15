package com.triprint.backend.domain.like.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.like.dto.LikeResponse;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.like.repository.LikeRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public LikeResponse registerLike(Long userId, Long postId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 사용자가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 게시물이 없습니다."));

		Like like = likeRepository.findByUserAndPost(user, post)
			.orElseGet(() -> Like.builder().user(user).post(post).build());

		likeRepository.save(like);
		return new LikeResponse(true);
	}

	@Transactional
	public LikeResponse unregisterLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 사용자가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 게시물이 없습니다."));

		likeRepository.findByUserAndPost(user, post).ifPresent(likeRepository::delete);
		return new LikeResponse(false);
	}
}
