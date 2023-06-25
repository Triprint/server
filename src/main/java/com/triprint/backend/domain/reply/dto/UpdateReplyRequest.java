package com.triprint.backend.domain.reply.dto;

import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public class UpdateReplyRequest {
	@NonNull
	private String contents;
}
