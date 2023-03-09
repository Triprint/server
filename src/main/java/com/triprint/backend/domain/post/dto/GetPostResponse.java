package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.Getter;

@Getter
public class GetPostResponse {
	private Long id;
	private AuthorInfoResponse author;
	private String title;
	private String contents;
	private Integer likes;
	private Boolean isLike;
	private List<String> images = new ArrayList<>();
	private List<String> hashtags = new ArrayList<>();
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private GetTouristAttractionResponse touristAttraction;

	public GetPostResponse(Post post) {
		List<String> images = post.getImages().stream().map(image -> image.getPath()).collect(Collectors.toList());
		List<String> hashtags = post.getPostHashtag()
			.stream()
			.map(postHashtag -> postHashtag.getHashtag().getContents())
			.collect(
				Collectors.toList());

		this.id = post.getId();
		this.author = new AuthorInfoResponse(post.getAuthor());
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.touristAttraction = new GetTouristAttractionResponse(post.getTouristAttraction());
		this.likes = post.getLikes().size();
		this.images = images;
		this.hashtags = hashtags;
		this.createdAt = post.getCreatedAt();
	}
}
