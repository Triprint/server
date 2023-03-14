package com.triprint.backend.domain.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public LikeDto registerLike(Long userId, Long postId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("일치하는 user 가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("일치하는 post 가 없습니다."));

		Like like = likeRepository.getUserIdAndPostId(userId, postId)
			.orElseGet(() -> Like.builder().user(user).post(post).build());

		// if (like.isEmpty()) {
		// 	like = Optional.of(
		// 		Like.builder()
		// 			.user(user)
		// 			.post(post)
		// 			.build()
		// 	);
		// }
		// Like newLike = like.get();

		try {
			activate(like);
		} catch (RuntimeException e) {
			System.err.println("like 가 생성되지 않았습니다.");
		}

		return new LikeDto(like.isStatus());
	}

	public LikeDto unregisterLike(Long userId, Long postId) {
		// User user = userRepository.findById(userId)
		// 	.orElseThrow(() -> new RuntimeException("일치하는 user 가 없습니다."));
		//
		// Post post = postRepository.findById(postId)
		// 	.orElseThrow(() -> new RuntimeException("일치하는 post 가 없습니다."));

		Like like = likeRepository.getUserIdAndPostId(userId, postId)
			.orElseThrow(() -> new RuntimeException("일치하는 like 가 없습니다."));

		inactive(like);

		return new LikeDto(like.isStatus());
	}

	@Transactional
	public void activate(Like like) {
		like.changeStatus(true);
		likeRepository.save(like);
	}

	@Transactional
	public void inactive(Like like) {
		like.changeStatus(false);
		likeRepository.save(like);
	}

}
