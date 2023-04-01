package com.triprint.backend.domain.post.controller;

import java.util.List;

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
import com.triprint.backend.domain.post.dto.CreatePostRequest;
import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.post.dto.PostResponse;
import com.triprint.backend.domain.post.dto.UpdatePostRequest;
import com.triprint.backend.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@GetMapping()
	public ResponseEntity<Page<GetPostResponse>> getPostList(
		@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page,
		@CurrentUser UserPrincipal userPrincipal) {
		return ResponseEntity.ok(postService.getPostList(page, userPrincipal));
	}

	@GetMapping("/like")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<GetPostResponse>> getLikePostList(@CurrentUser UserPrincipal userPrincipal,
		@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {
		return ResponseEntity.ok(postService.getLikePostList(userPrincipal.getId(), page));
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostResponse> createPost(
		@Valid @RequestPart(value = "data") CreatePostRequest createPostRequest,
		@ValidFileFormat(format = {"image/jpeg",
			"image/png"}) @ValidFileSize(maxSize = 5242880) @RequestPart(value = "images") List<MultipartFile> images,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(postService.create(userPrincipal.getId(), createPostRequest, images),
			HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetPostResponse> getPost(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
		return new ResponseEntity<>(postService.getPost(id, userPrincipal), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
		@RequestPart(value = "data") @Valid UpdatePostRequest postUpdateRequest,
		@CurrentUser UserPrincipal userPrincipal,
		@ValidFileFormat(format = {"image/jpeg",
			"image/png"}) @ValidFileSize(maxSize = 5242880) @RequestPart(value = "images") List<MultipartFile> images) {
		return new ResponseEntity<>(postService.updatePost(id, postUpdateRequest, userPrincipal.getId(), images),
			HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
		postService.deletePost(id, userPrincipal.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
