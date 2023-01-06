package com.triprint.backend.domain.user.dto;

import lombok.Builder;

public class MyProfileImgResponse {
	private String profileImg;

	@Builder
	public MyProfileImgResponse(String profileImg) {
		this.profileImg = profileImg;
	}
}
