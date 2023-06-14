package com.triprint.backend.domain.follow.dto;

import com.triprint.backend.domain.user.dto.AuthorInfoResponse;
import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFollowResponse {
	private AuthorInfoResponse follower;
	private boolean isFollow;

	public GetFollowResponse(User follower, boolean isFollow) {
		this.follower = new AuthorInfoResponse(follower);
		this.isFollow = isFollow;
	}
}
