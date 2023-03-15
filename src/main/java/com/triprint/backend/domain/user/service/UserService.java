package com.triprint.backend.domain.user.service;

import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findById(Long userid) {
		return userRepository.findById(userid).orElseThrow(() ->
			new ResourceNotFoundException("일치하는 user 가 없습니다."));
	}
}
