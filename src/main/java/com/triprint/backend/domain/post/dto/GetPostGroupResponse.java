package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.triprint.backend.domain.post.entity.PostGroup;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.Getter;

@Getter
public class GetPostGroupResponse {
	private final Long id;
	private final AuthorInfoResponse author;
	private final String title;
	private List<PostDto> postList = new ArrayList<>();
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	public GetPostGroupResponse(PostGroup posts) {
		this.id = posts.getId();
		this.author = new AuthorInfoResponse(posts.getUser());
		posts.getPosts().forEach((post) -> {
			this.postList.add(new PostDto(post));
		});
		this.title = posts.getTitle();
		this.createdAt = posts.getCreatedAt();
		this.updatedAt = posts.getUpdatedAt();
	}
}
