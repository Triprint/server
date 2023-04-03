package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class PostDto {
	private Long id;
	private String title;
	private List<String> images = new ArrayList<>();
	private List<String> hashtags = new ArrayList<>();
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private GetTouristAttractionResponse touristAttraction;

	public PostDto(Post post) {
		List<String> images = post.getImages().stream().map(Image::getPath).collect(Collectors.toList());

		List<String> hashtags = post.getPostHashtag()
			.stream()
			.map(postHashtag -> postHashtag.getHashtag().getContents())
			.collect(
				Collectors.toList());

		this.id = post.getId();
		this.title = post.getTitle();
		this.createdAt = post.getCreatedAt();
		this.touristAttraction = new GetTouristAttractionResponse(post.getTouristAttraction());
		this.updatedAt = post.getUpdatedAt();
		this.images = images;
		this.hashtags = hashtags;
	}
}
