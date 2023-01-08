package com.triprint.backend.domain.user.repository;

import com.triprint.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 기본적인 CRUD 함수를 가지고 있음
// JpaRepository를 상속했기 때문에 @Repository 어노테이션 불필요
public interface UserRepository extends JpaRepository<User, Long> {

//     JPA findBy 규칙
//     select * user_master where kakao_id = ?
    Optional<User> findByEmail(String kakaoEmail);

}
