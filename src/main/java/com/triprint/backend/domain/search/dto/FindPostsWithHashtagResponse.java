package com.triprint.backend.domain.search.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

	@Positive
	private Long id;

	@Nullable
	private String title;

	@Nullable
	private String contents;

	@NonNull
	private List<String> images;

	@Nullable
	private List<String> hashTags;

	@NonNull
	private AuthorInfoResponse author;

	@Positive
	@Nullable
	private Long tripId;

	@PositiveOrZero
	private Integer likes;

	@NonNull
	private GetTouristAttractionResponse touristAttraction;

	@PastOrPresent
	@NonNull
	private Timestamp createdAt;

	@PastOrPresent
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
