package com.triprint.backend.domain.post.controller;

import com.triprint.backend.domain.post.dto.PostListDto;
import com.triprint.backend.domain.post.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@GetMapping("/")
	public ResponseEntity<List<PostListDto>> getPostList() {
		return ResponseEntity.ok(postService.getPostList());
	}

	@GetMapping("/like")
	public ResponseEntity<List<PostListDto>> getLikePostList(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");

		return ResponseEntity.ok(postService.getLikePostList(userId));
	}
}
