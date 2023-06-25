package com.triprint.backend.domain.trip.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.triprint.backend.domain.post.dto.PostDto;
import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.user.dto.UserInfoResponse;

import lombok.Getter;

@Getter
public class GetTripResponse {
	private final Long id;
	private final UserInfoResponse author;
	private final String title;
	private final List<PostDto> postList;
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	public GetTripResponse(Trip trip) {
		this.id = trip.getId();
		this.author = new UserInfoResponse(trip.getUser());
		this.postList = new ArrayList<>();
		trip.getPosts().forEach((post) -> {
			this.postList.add(new PostDto(post));
		});
		this.title = trip.getTitle();
		this.createdAt = trip.getCreatedAt();
		this.updatedAt = trip.getUpdatedAt();
	}
}
