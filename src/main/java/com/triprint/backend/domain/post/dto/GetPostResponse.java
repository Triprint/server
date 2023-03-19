package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.Getter;

@Getter
public class GetPostResponse {
	private final Long id;
	private final AuthorInfoResponse author;
	private final String title;
	private final String contents;
	private final Integer likes;
	private final List<String> images;
	private final List<String> hashtags;
	private final Timestamp createdAt;
	private Timestamp updatedAt;
	private final GetTouristAttractionResponse touristAttraction;
	private final Boolean isLike;

	public GetPostResponse(Post post, Boolean isLike) {
		List<String> images = post.getImages().stream().map(Image::getPath).collect(Collectors.toList());

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
		this.isLike = isLike;
		this.images = images;
		this.hashtags = hashtags;
		this.createdAt = post.getCreatedAt();
	}
}
