package com.triprint.backend.domain.search.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PredictiveHashtagResponse {

	private Long tagId;

	private String tagName;

	private Long postCnt;

	public PredictiveHashtagResponse(Long tagId, String tagName, Long postCnt) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.postCnt = postCnt;
	}
}
