package com.triprint.backend.domain.auth.security.service;

import org.springframework.stereotype.Service;

import com.triprint.backend.domain.auth.security.entity.UserRefreshToken;
import com.triprint.backend.domain.auth.security.repository.UserRefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final UserRefreshTokenRepository userRefreshTokenRepository;

	public void findOrCreate(Long userId, String refreshToken) {
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
		if (userRefreshToken != null) {
			userRefreshToken.setRefreshToken(refreshToken);
		} else {
			userRefreshToken = UserRefreshToken.builder()
				.userId(userId)
				.refreshToken(refreshToken)
				.build();
		}
		userRefreshTokenRepository.saveAndFlush(userRefreshToken);
	}
}
