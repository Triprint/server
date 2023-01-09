package com.triprint.backend.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeDto {
	boolean status;

	public LikeDto(boolean status) {
		this.status = status;
	}
}
