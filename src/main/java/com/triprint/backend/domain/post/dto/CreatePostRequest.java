package com.triprint.backend.domain.post.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import com.triprint.backend.domain.location.dto.CreateTouristAttractionRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
	@Nullable
	@Size(max = 50)
	private String title;

	@Nullable
	@Size(max = 500)
	private String content;

	private List<String> hashtag = new ArrayList<>();
	@NotNull
	private CreateTouristAttractionRequest touristAttraction;
}
