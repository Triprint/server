package com.triprint.backend.domain.post.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.post.dto.CreateOrUpdatePostGroupRequest;
import com.triprint.backend.domain.post.dto.GetPostGroupResponse;
import com.triprint.backend.domain.post.dto.PostGroupResponse;
import com.triprint.backend.domain.post.service.PostGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/trip")
public class PostGroupController {

	private final PostGroupService postGroupService;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostGroupResponse> createPostGroup(
		@Valid @RequestBody CreateOrUpdatePostGroupRequest createOrUpdatePostGroupRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(postGroupService.create(userPrincipal.getId(), createOrUpdatePostGroupRequest),
			HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetPostGroupResponse> getPost(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long id) {
		return new ResponseEntity<>(postGroupService.getPostGroup(id, userPrincipal), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostGroupResponse> updatePostGroup(
		@PathVariable Long id,
		@Valid @RequestBody CreateOrUpdatePostGroupRequest createOrUpdatePostGroupRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(postGroupService.update(id, userPrincipal.getId(), createOrUpdatePostGroupRequest),
			HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
		postGroupService.delete(id, userPrincipal.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
