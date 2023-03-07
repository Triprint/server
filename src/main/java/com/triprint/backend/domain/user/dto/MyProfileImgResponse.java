package com.triprint.backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyProfileImgResponse {
	private final String profileImg;

	@Builder
	public MyProfileImgResponse(String profileImg) {
		this.profileImg = profileImg;
	}
}
