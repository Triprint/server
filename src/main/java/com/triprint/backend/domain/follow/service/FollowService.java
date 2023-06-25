package com.triprint.backend.domain.follow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.follow.dto.GetFollowResponse;
import com.triprint.backend.domain.follow.entity.Follow;
import com.triprint.backend.domain.follow.repository.FollowRepository;
import com.triprint.backend.domain.user.dto.UserInfoResponse;
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
			.orElseGet(() -> Follow.builder()
				.follower(currentUser)
				.following(followUser)
				.build());

		followRepository.save(follow);
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

	public Page<UserInfoResponse> getMyFollowers(Long userId, Pageable pageable) { //'나'를 팔로우한 사람들
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followers = followRepository.findAllByFollowing(user, pageable);
		return followers.map(follow -> new UserInfoResponse(follow.getFollower()));
	}

	public Page<GetFollowResponse> getFollowers(Long currentUserId, Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followers = followRepository.findAllByFollower(user, pageable);
		return followers.map((follow) -> {
			boolean isFollow = this.isFollow(currentUser, follow.getFollowing());
			return new GetFollowResponse(follow.getFollowing(), isFollow);
		});
	}

	public Page<UserInfoResponse> getMyFollowings(Long userId, Pageable pageable) { //'나'의 팔로우를 받는 사람들
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followings = followRepository.findAllByFollower(user, pageable);
		return followings.map(follow -> new UserInfoResponse(follow.getFollowing()));
	}

	public Page<GetFollowResponse> getFollowings(Long currentUserId, Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<Follow> followings = followRepository.findAllByFollowing(user, pageable);
		return followings.map(
			(follow) -> {
				boolean isFollow = this.isFollow(currentUser, follow.getFollower());
				return new GetFollowResponse(follow.getFollower(), isFollow);
			});
	}

	public boolean isFollow(User currentUser, User user) {
		List<Follow> followings = currentUser.getFollowings();
		return followings.stream().anyMatch((following) -> following.getFollower().equals(user));
	}
}
