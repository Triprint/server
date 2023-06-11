package com.triprint.backend.domain.follow.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.follow.dto.FollowUserRequest;
import com.triprint.backend.domain.follow.service.FollowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/follow")
public class FollowController {
	private final FollowService followService;

	@PutMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<Object> followUser(@CurrentUser UserPrincipal userPrincipal,
		@Valid FollowUserRequest followUserRequest) {
		followService.followUser(userPrincipal.getId(), followUserRequest.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PutMapping("/un")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<Object> unfollowUser(@CurrentUser UserPrincipal userPrincipal,
		@Valid FollowUserRequest followUserRequest) {
		followService.unfollowUser(userPrincipal.getId(), followUserRequest.getId());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/follower")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<Object> get(@CurrentUser UserPrincipal userPrincipal) {
		followService.getFollowers(userPrincipal.getId());
		return ResponseEntity.ok().build();
	}

}
