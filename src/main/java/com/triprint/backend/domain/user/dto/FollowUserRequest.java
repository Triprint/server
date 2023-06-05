package com.triprint.backend.domain.user.dto;

import com.triprint.backend.core.valid.ValidId;

import lombok.Getter;

@Getter
public class FollowUserRequest {
	@ValidId
	private Long id;
}
