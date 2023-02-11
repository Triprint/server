package com.triprint.backend.domain.user.service;

import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.dto.UserDto;
import com.triprint.backend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public User findById(Long userid){
        return userRepository.findById(userid).orElseThrow(() -> {
            throw new RuntimeException("올바른 사용자가 아닙니다.");
        });
    }

    public User findByusername(UserDto userDto){
        return userRepository.findByusername(userDto.getUsername());
    }
}
