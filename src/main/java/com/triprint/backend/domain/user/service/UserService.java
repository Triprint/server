package com.triprint.backend.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triprint.backend.domain.user.config.jwt.JwtProperties;
import com.triprint.backend.domain.user.config.jwt.JwtRequestFilter;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.entity.kakaoLoginDto.KakaoProfile;
import com.triprint.backend.domain.user.entity.kakaoLoginDto.OauthToken;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.triprint.backend.domain.user.config.SecurityConfig.FRONT_URL;
import static com.triprint.backend.domain.user.status.UserRole.USER;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //환경 변수 가져오기
    @Value("355bc84f673d4cd4b5d9c1b88838cf32")
    String client_id;

    @Value("${CLIENT_SECRET}")
    String client_secret;

    public OauthToken getAccessToken(String code) {

        // POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", FRONT_URL + "/auth");
        params.add("code", code);
        params.add("client_secret", client_secret);

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
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
        // POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public User getUser(HttpServletRequest request) {
        String userCode = (String) request.getAttribute("userCode");

        User user = userRepository.findBykakaoId(userCode);

        return user;
    }

    public String SaveUserAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByEmail(profile.getKakao_account().getEmail());
        if (user == null) {
            user = User.builder()
                    .kakaoId(profile.getId().toString())
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .userRole(USER).build();

            userRepository.save(user);
        }

        return createToken(user);
    }

    public String createToken(User user) {
        // Jwt 생성 후 헤더에 추가해서 보내줌
        String jwtToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }
}
