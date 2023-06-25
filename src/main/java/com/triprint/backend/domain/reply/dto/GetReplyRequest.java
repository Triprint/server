package com.triprint.backend.domain.reply.dto;

import com.triprint.backend.core.valid.ValidId;

import lombok.Getter;

@Getter
public class GetReplyRequest {
	@ValidId
	private Long postId;
}
