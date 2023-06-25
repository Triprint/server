package com.triprint.backend.domain.search.dto;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictiveHashtagRequest {
	@NonNull
	private String keyword;
}
