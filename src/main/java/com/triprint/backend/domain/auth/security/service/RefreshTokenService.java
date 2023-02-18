package com.triprint.backend.domain.auth.security.service;

import com.triprint.backend.domain.user.entity.UserRefreshToken;
import com.triprint.backend.domain.user.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public void findOrCreate(Long userId, String refreshToken){
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
