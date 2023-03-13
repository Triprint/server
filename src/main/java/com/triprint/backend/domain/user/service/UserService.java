package com.triprint.backend.domain.user.service;

import org.springframework.stereotype.Service;

import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findById(Long userid) {
		return userRepository.findById(userid).orElseThrow(() -> {
			throw new RuntimeException("올바른 사용자가 아닙니다.");
		});
	}
}
