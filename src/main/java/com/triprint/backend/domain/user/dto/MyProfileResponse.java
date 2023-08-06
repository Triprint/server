package com.triprint.backend.domain.user.dto;

import com.triprint.backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class MyProfileResponse extends UserInfoResponse {

	private int myTrips;
	private int myFollowers;
	private int myFollowings;

	public MyProfileResponse(User user) {
		super(user);
		myTrips = user.getTrip().size();
		myFollowers = user.getFollowers().size();
		myFollowings = user.getFollowings().size();
	}
}
