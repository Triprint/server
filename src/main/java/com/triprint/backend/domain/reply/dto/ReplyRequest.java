package com.triprint.backend.domain.reply.dto;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class ReplyRequest {

	@Nullable
	private Long subReplyUserId;

	private String contents;

	private Long postId;

	@Nullable
	private Long parentReplyId;
}
