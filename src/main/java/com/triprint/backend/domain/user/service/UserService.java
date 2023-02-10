package com.triprint.backend.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triprint.backend.domain.user.config.jwt.JwtProperties;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.dto.kakaoLoginDto.KakaoProfile;
import com.triprint.backend.domain.user.dto.kakaoLoginDto.OauthToken;
import com.triprint.backend.domain.user.dto.UserDto;
import com.triprint.backend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.triprint.backend.domain.user.status.UserRole.USER;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.clientId}")
    String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
    String client_secret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    String redirectUri;

    public OauthToken getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", client_secret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new RuntimeException("email에 해당하는 User가 존재하지 않습니다.");
        });
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImg(user.getProfileImg())
                .build();
    }

    public String SaveUserAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);

        Optional<User> user = userRepository.findByEmail(profile.getKakao_account().getEmail());
        if (!user.isPresent()) {
            User newUser = User.builder()
                    .kakaoId(profile.getId().toString())
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .userRole(USER).build();

            userRepository.save(newUser);
            return createToken(newUser);
        }

        return createToken(user.get());
    }

    public String createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.TOKEN_SECRET_KEY));

        return jwtToken;
    }

    public User findById(Long userid){
        return userRepository.findById(userid).orElseThrow(() -> {
            throw new RuntimeException("올바른 사용자가 아닙니다.");
        });
    }

    public User findByusername(UserDto userDto){
        return userRepository.findByusername(userDto.getUsername());
    }
}
