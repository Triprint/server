package com.triprint.backend.domain.search.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class GetLocationResponse {
	@NonNull
	private Long cityId;

	@Nullable
	private Long districtId;
}
