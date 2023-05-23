package com.triprint.backend.domain.search.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.post.entity.PostHashtag;
import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;
import com.triprint.backend.domain.user.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindPostsWithHashtagResponse {

	private Long id;
	private String title;
	private String contents;
	private List<String> images;
	private List<String> hashTags;
	private AuthorInfoResponse author;
	private Long tripId;
	private Integer likes;
	private GetTouristAttractionResponse touristAttraction;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public FindPostsWithHashtagResponse(Long id, String title, String contents, List<Image> images,
		List<PostHashtag> hashtags, User author,
		Trip trip, List<Like> likes, TouristAttraction touristAttraction, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.images = images.stream().map(Image::getPath).collect(Collectors.toList());
		this.hashTags = hashtags.stream()
			.map(postHashtag -> postHashtag.getHashtag().getContents())
			.collect(
				Collectors.toList());
		this.author = new AuthorInfoResponse(author);
		this.tripId = trip.getId();
		this.likes = likes.size();
		this.touristAttraction = new GetTouristAttractionResponse(touristAttraction);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
