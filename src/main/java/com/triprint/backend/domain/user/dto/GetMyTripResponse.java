package com.triprint.backend.domain.user.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.triprint.backend.domain.post.dto.PostDto;
import com.triprint.backend.domain.trip.entity.Trip;

import lombok.Getter;

@Getter
public class GetMyTripResponse {
	private Long id;
	private String title;
	private List<PostDto> postList;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public GetMyTripResponse(Trip trip) {
		this.id = trip.getId();
		this.title = trip.getTitle();
		this.postList = new ArrayList<>();
		trip.getPosts().forEach((post) -> {
			this.postList.add(new PostDto(post));
		});
		this.createdAt = trip.getCreatedAt();
		this.updatedAt = trip.getUpdatedAt();
	}
}
