package com.triprint.backend.domain.user.controller;

import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.entity.UserDto;
import com.triprint.backend.domain.user.kakaoLoginDto.OauthToken;
import com.triprint.backend.domain.user.kakaoLoginDto.KakaoDto;
import com.triprint.backend.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    // 프론트에서 인가코드 돌려 받는 주소
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    @GetMapping("/oauth/kakao")
    public ResponseEntity getLogin(@RequestParam("code") String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = userService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String jwtToken = userService.SaveUserAndGetToken(oauthToken.getAccess_token());

        //토큰을 프론트에 넘겨주기
        KakaoDto kakaodto = KakaoDto.builder().token(jwtToken).build();
        return ResponseEntity.ok().body(kakaodto);
    }

    // jwt 토큰으로 유저정보 요청하기
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        // 1. 정상적인 동작인 경우(토큰이 생성된 경우) 올바른 처리하기(userService)
        // 2. merge(충돌해결 후)

        UserDto user = userService.getUser(userId);

        return ResponseEntity.ok().body(user);
    }

}

