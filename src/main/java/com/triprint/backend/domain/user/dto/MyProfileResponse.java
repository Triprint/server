package com.triprint.backend.domain.user.dto;

import org.springframework.data.domain.Page;

import com.triprint.backend.domain.trip.dto.GetMyTripResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyProfileResponse {
	private Long id;
	private String email;
	private String username;
	private String profileImg;
	private Page<GetMyTripResponse> myTrips;
	private Page<UserInfoResponse> myFollowers;
	private Page<UserInfoResponse> myFollowings;
}
