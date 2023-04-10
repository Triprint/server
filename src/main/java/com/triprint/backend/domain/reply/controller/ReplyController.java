package com.triprint.backend.domain.reply.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.reply.dto.GetReplyResponse;
import com.triprint.backend.domain.reply.dto.ReplyRequest;
import com.triprint.backend.domain.reply.dto.ReplyResponse;
import com.triprint.backend.domain.reply.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ReplyResponse> createReply(
		@Valid @RequestBody ReplyRequest replyRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(replyService.create(userPrincipal.getId(), replyRequest),
			HttpStatus.CREATED);
	}

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<GetReplyResponse>> getReplies(@CurrentUser UserPrincipal userPrincipal,
		@RequestParam("postId") Long postId,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
		return ResponseEntity.ok(replyService.getReplies(postId, page));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ReplyResponse> updateReply(@PathVariable Long id,
		@Valid @RequestBody ReplyRequest replyRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(replyService.update(id, replyRequest, userPrincipal.getId()),
			HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> delete(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
		replyService.delete(id, userPrincipal.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
