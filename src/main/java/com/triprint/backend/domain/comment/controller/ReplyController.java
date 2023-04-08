package com.triprint.backend.domain.comment.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.comment.dto.CreateReplyRequest;
import com.triprint.backend.domain.comment.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Long> createPostGroup(
		@Valid @RequestBody CreateReplyRequest createReplyRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(replyService.create(userPrincipal.getId(), createReplyRequest),
			HttpStatus.CREATED);
	}
}
