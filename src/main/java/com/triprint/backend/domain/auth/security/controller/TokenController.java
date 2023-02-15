package com.triprint.backend.domain.auth.security.controller;

import com.triprint.backend.domain.auth.security.AuthToken;
import com.triprint.backend.domain.auth.security.TokenProvider;
import com.triprint.backend.domain.auth.security.common.ApiResponse;
import com.triprint.backend.domain.auth.security.config.AppProperties;
import com.triprint.backend.domain.user.entity.UserRefreshToken;
import com.triprint.backend.domain.user.repository.UserRefreshTokenRepository;
import com.triprint.backend.domain.user.status.UserRole;
import com.triprint.backend.domain.user.util.CookieUtils;
import com.triprint.backend.domain.user.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;


    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response){
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
//        if (!authToken.validate()){
//            return ApiResponse.invalidAccessToken();
//        }

        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null){
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        UserRole userRole = UserRole.of(claims.get("role", String.class));

        String refreshToken = CookieUtils.getCookie(request, "refresh_token")
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertRefreshToken(refreshToken);

        if(!authRefreshToken.validate()){
            return ApiResponse.invalidRefreshToken();
        }

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null){
            return  ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                Long.parseLong(userId),
                userRole.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiration())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        if (validTime <= 259200000 ){
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiration();
            authRefreshToken = tokenProvider.createAuthToken(
                    Long.parseLong(userId),
                    new Date(now.getTime() + refreshTokenExpiry)
            );
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);

            int cookieMaxAge = (int) refreshTokenExpiry / 1000;
            CookieUtils.deleteCookie(request, response, "refresh_token");
            CookieUtils.addCookie(response, "refresh_token", authRefreshToken.getToken(), cookieMaxAge);
        }
        return  ApiResponse.success("token", newAccessToken.getToken());
    }
}
