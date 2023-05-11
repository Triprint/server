package com.triprint.backend.domain.search.dto;

import java.sql.Timestamp;
import java.util.List;

import com.triprint.backend.domain.location.dto.GetTouristAttractionResponse;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

public interface PostDtoInterface {
	long getId();

	String getTitle();

	String getContents();

	List<String> getImages();

	List<String> getHashTags();

	AuthorInfoResponse getAuthor();

	Integer getLikes();

	GetTouristAttractionResponse getTouristAttraction();
	
	Timestamp getCreatedAt();

	Timestamp getUpdatedAt();
}
