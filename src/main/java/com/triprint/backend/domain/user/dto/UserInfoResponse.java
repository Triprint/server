package com.triprint.backend.domain.user.dto;

import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserInfoResponse {
	private Long id;
	private String username;
	private String profileImg;

	public UserInfoResponse(User author) {
		this.id = author.getId();
		this.username = author.getUsername();
		this.profileImg = author.getProfileImg();
	}
}
