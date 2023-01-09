package com.triprint.backend.domain.like.service;

import com.triprint.backend.domain.like.dto.LikeDto;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.like.repository.LikeRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		Optional<Like> like = likeRepository.findByUserAndPost(user, post);

		if (like.isEmpty()) {
			like = Optional.of(
				Like.builder()
					.user(user)
					.post(post)
					.build()
			);
		}

		Like newLike = like.get();

		try {
			activateLike(newLike);
		} catch (RuntimeException e) {
			System.err.println("like 가 생성되지 않았습니다.");
		}

		return new LikeDto(newLike.isStatus());
	}

	public LikeDto unregisterLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("일치하는 user 가 없습니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("일치하는 post 가 없습니다."));

		Like like = likeRepository.findByUserAndPost(user, post)
			.orElseThrow(() -> new RuntimeException("일치하는 like 가 없습니다."));

		deactivateLike(like);

		return new LikeDto(like.isStatus());
	}

	@Transactional
	public void activateLike(Like like) {
		like.changeStatus(true);
		likeRepository.save(like);
	}

	@Transactional
	public void deactivateLike(Like like) {
		like.changeStatus(false);
		likeRepository.save(like);
	}

}
