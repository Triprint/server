package com.triprint.backend.domain.follow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.follow.entity.Follow;
import com.triprint.backend.domain.user.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
