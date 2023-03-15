package com.triprint.backend.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.like.dto.LikeResponse;
import com.triprint.backend.domain.like.service.LikeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/{postId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<LikeResponse> activateLike(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long postId) {
		return ResponseEntity.ok(likeService.registerLike(userPrincipal.getId(), postId));
	}

	@PutMapping("/{postId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<LikeResponse> deactivateLike(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long postId) {
		return ResponseEntity.ok(likeService.unregisterLike(userPrincipal.getId(), postId));
	}
}
