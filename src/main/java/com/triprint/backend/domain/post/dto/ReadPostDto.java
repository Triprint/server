package com.triprint.backend.domain.post.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.triprint.backend.domain.location.dto.ReadTouristAttractionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadPostDto {
	private String authorName;
	@Nullable
	private String title;
	@Nullable
	private String content;
	@Nullable
	private List<String> images = new ArrayList<>();
	@Nullable
	private List<String> hashtags = new ArrayList<>();
	private Timestamp createdAt;
	private Timestamp updatedAt;
	@NotNull
	private ReadTouristAttractionDto touristAttraction;
}
