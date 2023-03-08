package com.triprint.backend.domain.post.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.core.valid.ValidFileFormat;
import com.triprint.backend.core.valid.ValidFileSize;
import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.PostListDto;
import com.triprint.backend.domain.post.dto.UpdatePostDto;
import com.triprint.backend.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@GetMapping()
	public ResponseEntity<Page<PostListDto>> getPostList(
		@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable page) {
		return ResponseEntity.ok(postService.getPostList(page));
	}

	@GetMapping("/like")
	public ResponseEntity<Page<PostListDto>> getLikePostList(HttpServletRequest request,
		@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable page) {
		Long userId = (Long)request.getAttribute("userId");

		return ResponseEntity.ok(postService.getLikePostList(userId, page));
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Long> createPost(@Valid @RequestPart(value = "data") CreatePostDto createPostDto,
		@ValidFileFormat(format = {"image/jpeg",
			"image/png"}) @ValidFileSize(maxSize = 1024) @RequestPart(value = "images") List<MultipartFile> images,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(postService.create(userPrincipal.getId(), createPostDto, images),
			HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity readPost(@PathVariable Long id) {
		return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity updatePost(@PathVariable Long id,
		@RequestPart(value = "data") @Valid UpdatePostDto postUpdateRequestDto, HttpServletRequest request,
		@RequestPart(value = "images") List<MultipartFile> images) {
		return new ResponseEntity<>(postService.updatePost(id, postUpdateRequestDto, request, images), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request) {
		postService.deletePost(id, request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
