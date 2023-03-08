package com.triprint.backend.domain.user.service;

import org.springframework.stereotype.Service;

import com.triprint.backend.domain.user.dto.UserInfoResponse;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserInfoResponse getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new RuntimeException("email에 해당하는 User가 존재하지 않습니다.");
		});
		return UserInfoResponse.builder()
			.email(user.getEmail())
			.username(user.getUsername())
			.profileImg(user.getProfileImg())
			.build();
	}

	public User findById(Long userid) {
		return userRepository.findById(userid).orElseThrow(() -> {
			throw new RuntimeException("올바른 사용자가 아닙니다.");
		});
	}
}
