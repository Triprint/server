package com.triprint.backend.domain.follow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.follow.entity.Follow;
import com.triprint.backend.domain.follow.repository.FollowRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	@Transactional
	public void followUser(Long followingId, Long followerId) {
		User following = userRepository.findById(followingId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User follower = userRepository.findById(followerId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
			.orElseGet(() -> this.createFollow(follower, following));
	}

	@Transactional
	public Follow createFollow(User follower, User following) {

		Follow follow = Follow.builder()
			.follower(follower)
			.following(following)
			.build();
		return followRepository.save(follow);
	}
}
