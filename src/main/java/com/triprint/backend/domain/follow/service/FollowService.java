package com.triprint.backend.domain.follow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final UserRepository userRepository;

	@Transactional
	public void followUser(Long currentUserId, Long followUserId) {
		User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		User followUser = userRepository.findById(currentUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		currentUser.followUser(followUser);
	}
}
