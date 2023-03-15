package com.triprint.backend.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeResponse {
	boolean status;

	public LikeResponse(boolean status) {
		this.status = status;
	}
}
