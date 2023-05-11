package com.triprint.backend.domain.search.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PredictiveHashtagResponse {
	private List<String> hashTags;
	private int relatedPostCnt;
}
