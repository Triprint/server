package com.triprint.backend.domain.comment.dto;

import lombok.Getter;

@Getter
public class CreateReplyRequest {
	private Long subReplyUserId;
	private String contents;
	private Long postId;
	private Long parentReplyId;
}
