package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostListDto {

	private String author;
	private String title;
	private String contents;
	private Integer likes;
	private List<String> images;
	private Timestamp createdDate;

	@Builder
	PostListDto(String author, String title, String contents, Integer likes, List<String> images,
		Timestamp createdDate) {
		this.author = author;
		this.title = title;
		this.contents = contents;
		this.likes = likes;
		this.images = images;
		this.createdDate = createdDate;
	}
}
