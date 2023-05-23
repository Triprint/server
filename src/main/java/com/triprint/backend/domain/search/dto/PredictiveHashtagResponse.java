package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PredictiveHashtagResponse {
	@NonNull
	@Positive
	private Long tagId;

	@NonNull
	private String tagName;

	@Nullable
	private Long postCnt;

	public PredictiveHashtagResponse(Long tagId, String tagName, Long postCnt) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.postCnt = postCnt;
	}
}
