package com.triprint.backend.domain.follow.dto;

import com.triprint.backend.domain.user.dto.UserInfoResponse;
import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFollowResponse extends UserInfoResponse {
	private boolean isFollow;

	public GetFollowResponse(User follower, boolean isFollow) {
		super(follower);
		this.isFollow = isFollow;
	}
}
