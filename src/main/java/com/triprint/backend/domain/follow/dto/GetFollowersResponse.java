package com.triprint.backend.domain.follow.dto;

import java.util.List;

import com.triprint.backend.domain.user.dto.AuthorInfoResponse;
import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class GetFollowersResponse {
	private AuthorInfoResponse user;
	private List<AuthorInfoResponse> followers;

	public GetFollowersResponse(User user, List<AuthorInfoResponse> followers) {

		this.user = new AuthorInfoResponse(user);
		this.followers = followers;
	}
}
