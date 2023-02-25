package com.triprint.backend.domain.auth.security.service;

import com.triprint.backend.domain.auth.security.AuthToken;
import com.triprint.backend.domain.auth.security.TokenProvider;
import com.triprint.backend.core.config.AppProperties;
import com.triprint.backend.domain.auth.security.entity.UserRefreshToken;
import com.triprint.backend.domain.auth.security.oauth2.exception.InvalidRefreshTokenException;
import com.triprint.backend.domain.auth.security.oauth2.exception.NotExpiredTokenYetException;
import com.triprint.backend.domain.auth.security.repository.UserRefreshTokenRepository;
import com.triprint.backend.domain.user.status.UserRole;
import com.triprint.backend.domain.user.util.CookieUtils;
import com.triprint.backend.domain.user.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public String refresh(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);

        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            throw new NotExpiredTokenYetException("아직 만료되지 않은 토큰입니다.");
        }

        Long userId = Long.parseLong(claims.getSubject());
        UserRole userRole = UserRole.of(claims.get("role", String.class));

        String refreshToken = CookieUtils.getCookie(request, "refresh_token")
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertRefreshToken(refreshToken);

        if (!authRefreshToken.validate()) {
            throw new InvalidRefreshTokenException("유효하지 않은 RefreshToken입니다.");
        }

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            throw new InvalidRefreshTokenException("해당 사용자는 RefreshToken이 존재하지 않습니다.");
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAccessToken(
                userId,
                userRole.getCode()
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        if (validTime <= 259200000) {
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiration();
            authRefreshToken = tokenProvider.createRefreshToken(
                    userId
            );
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken); //DB에 update쿼리 날리는 게 더 좋아보임 => 찾아보기

            int cookieMaxAge = (int) refreshTokenExpiry / 1000;
            CookieUtils.deleteCookie(request, response, "refresh_token");
            CookieUtils.addCookie(response, "refresh_token", authRefreshToken.getToken(), cookieMaxAge);
        }

        return newAccessToken.getToken();
    }
}








