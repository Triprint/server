package com.triprint.backend.domain.follow.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.follow.dto.GetFollowResponse;
import com.triprint.backend.domain.follow.entity.Follow;
import com.triprint.backend.domain.follow.repository.FollowRepository;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	@Transactional
	public void followUser(Long currentUserId, Long followUserId) {
		User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User followUser = userRepository.findById(followUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Follow follow = followRepository.findByFollowerAndFollowing(currentUser, followUser)
			.orElseGet(() -> this.createFollow(currentUser, followUser));
	}

	@Transactional
	public Follow createFollow(User follower, User following) {

		Follow follow = Follow.builder()
			.follower(follower)
			.following(following)
			.build();
		return followRepository.save(follow);
	}

	@Transactional
	public void unfollowUser(Long currentUserId, Long unfollowUserId) {
		User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User unfollowUser = userRepository.findById(unfollowUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Follow follow = followRepository.findByFollowerAndFollowing(currentUser, unfollowUser).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.FOLLOW_NOT_FOUND);
		});

		followRepository.delete(follow);
	}

	public Page<AuthorInfoResponse> getFollowers(Long userId, Pageable page) { //'나'를 팔로우한 사람들
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followers = followRepository.findAllByFollowing(user, page);
		return followers.map(follow -> new AuthorInfoResponse(follow.getFollower()));
	}

	public Page<AuthorInfoResponse> getFollowings(Long userId, Pageable page) { //'나'의 팔로우를 받는 사람들
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followings = followRepository.findAllByFollower(user, page);
		return followings.map(follow -> new AuthorInfoResponse(follow.getFollowing()));
	}

	public Page<GetFollowResponse> getOtherFollowers(Long currentUserId, Pageable page, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followers = followRepository.findAllByFollowing(user, page);
		return followers.map(
			follow -> new GetFollowResponse(follow.getFollower(), follow.getFollower().getId() == currentUserId));
	}

	public Page<GetFollowResponse> getOtherFollowings(Long currentUserId, Pageable page, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followings = followRepository.findAllByFollower(user, page);
		return followings.map(
			follow -> new GetFollowResponse(follow.getFollowing(), follow.getFollowing().getId() == currentUserId));
	}
}
