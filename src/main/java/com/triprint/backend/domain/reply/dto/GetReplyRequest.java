package com.triprint.backend.domain.reply.dto;

import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public class GetReplyRequest {
	@NonNull
	@Positive
	private Long postId;
}
