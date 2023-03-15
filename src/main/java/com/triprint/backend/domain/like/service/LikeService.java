package com.triprint.backend.domain.like.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.like.dto.LikeDto;
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
	public LikeDto registerLike(Long userId, Long postId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 user 가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 post 가 없습니다."));

		Like like = likeRepository.findByUserAndPost(user, post)
			.orElseGet(() -> Like.builder().user(user).post(post).build());

		likeRepository.save(like);
		return new LikeDto(true);
	}

	@Transactional
	public LikeDto unregisterLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 user 가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("일치하는 post 가 없습니다."));

		Optional<Like> like = likeRepository.findByUserAndPost(user, post);
		if (like.isPresent()) {
			likeRepository.delete(like.get());
		}
		return new LikeDto(false);
	}
}
