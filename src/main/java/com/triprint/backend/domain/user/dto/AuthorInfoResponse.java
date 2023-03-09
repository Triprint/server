package com.triprint.backend.domain.user.dto;

import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class AuthorInfoResponse {
	private Long id;
	private String username;
	private String profileImg;

	public AuthorInfoResponse(User author) {
		this.id = author.getId();
		this.username = author.getUsername();
		this.profileImg = author.getProfileImg();
	}
}
