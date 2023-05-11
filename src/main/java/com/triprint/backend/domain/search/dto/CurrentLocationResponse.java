package com.triprint.backend.domain.search.dto;

import java.sql.Timestamp;
import java.util.List;

import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CurrentLocationResponse implements PostDtoInterface {
	public long id;

	public String title;

	public String contents;

	public List<String> images;

	public List<String> hashTags;

	public AuthorInfoResponse author;

	public Long tripId;

	public Integer likes;

	public GetTouristAttractionResponse touristAttraction;

	public Timestamp createdAt;

	public Timestamp updatedAt;
}
