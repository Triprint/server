package com.triprint.backend.domain.reply.dto;

import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class CreateReplyRequest {

	@Nullable
	private Long subReplyUserId;

	@NonNull
	private String contents;

	@NonNull
	@Positive
	private Long postId;

	@Nullable
	private Long parentReplyId;
}
