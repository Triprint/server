package com.triprint.backend.domain.follow.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.follow.entity.Follow;
import com.triprint.backend.domain.user.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	Optional<Follow> findByFollowerAndFollowing(User follower, User following);

	Page<Follow> findAllByFollowing(User following, Pageable page); //TODO: 이게 왜 되지? 쿼리가 필요한 경우와 필요없는 경우

	Page<Follow> findAllByFollower(User follower, Pageable page);
}
