package com.triprint.backend.domain.follow.dto;

import com.triprint.backend.core.valid.ValidId;

import lombok.Getter;

@Getter
public class FollowUserRequest {
	@ValidId
	private Long id;
}
