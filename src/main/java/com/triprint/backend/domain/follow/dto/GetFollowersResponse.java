package com.triprint.backend.domain.follow.dto;

import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.Getter;

@Getter
public class GetFollowersResponse {
	private AuthorInfoResponse followers;

	public GetFollowersResponse(AuthorInfoResponse followers) {
		this.followers = followers;
	}
}
