package com.triprint.backend.domain.like.controller;

import com.triprint.backend.domain.like.dto.LikeDto;
import com.triprint.backend.domain.like.service.LikeService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/{postId}")
	public ResponseEntity<LikeDto> activateLike(HttpServletRequest request, @PathVariable Long postId) {
		Long userId = (Long) request.getAttribute("userId");


		return ResponseEntity.ok(likeService.registerLike(userId, postId));
	}

	@PutMapping("/{postId}")
	public ResponseEntity<LikeDto> deactivateLike(HttpServletRequest request, @PathVariable Long postId) {
		Long userId = (Long) request.getAttribute("userId");
		likeService.registerLike(userId, postId);

		return ResponseEntity.ok(likeService.registerLike(userId, postId));
	}
}
