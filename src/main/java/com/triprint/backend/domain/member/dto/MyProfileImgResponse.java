package com.triprint.backend.domain.member.dto;

import lombok.Builder;

public class MyProfileImgResponse {
	private String profileImg;

	@Builder
	public MyProfileImgResponse(String profileImg) {
		this.profileImg = profileImg;
	}
}
