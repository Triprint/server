package com.triprint.backend.domain.user.dto;

import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserInfoResponse {
	private Long id;
	private String email;
	private String username;
	private String profileImg;

	public UserInfoResponse(User author) {
		id = author.getId();
		email = author.getEmail();
		username = author.getUsername();
		profileImg = author.getProfileImg();
	}
}
