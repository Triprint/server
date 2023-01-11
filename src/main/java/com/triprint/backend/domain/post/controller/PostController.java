package com.triprint.backend.domain.post.controller;

import com.triprint.backend.domain.post.dto.PostListDto;
import com.triprint.backend.domain.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@GetMapping("")
	public ResponseEntity<Page<PostListDto>> getPostList(@PageableDefault(size=10, sort="id", direction = Direction.DESC) Pageable page) {
		return ResponseEntity.ok(postService.getPostList(page));
	}

	@GetMapping("/like")
	public ResponseEntity<Page<PostListDto>> getLikePostList(HttpServletRequest request, @PageableDefault(size=10, sort="id", direction = Direction.DESC) Pageable page) {
		Long userId = (Long) request.getAttribute("userId");

		return ResponseEntity.ok(postService.getLikePostList(userId, page));
	}
}
