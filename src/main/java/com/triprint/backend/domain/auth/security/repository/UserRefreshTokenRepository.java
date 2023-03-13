package com.triprint.backend.domain.auth.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triprint.backend.domain.auth.security.entity.UserRefreshToken;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
	UserRefreshToken findByUserId(Long userId);

	UserRefreshToken findByUserIdAndRefreshToken(Long userId, String refreshToken);
}
