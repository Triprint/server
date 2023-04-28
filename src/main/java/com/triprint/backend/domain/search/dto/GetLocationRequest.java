package com.triprint.backend.domain.search.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLocationRequest {
	@NonNull
	private Long cityId;

	@Nullable
	private Long districtId;
}
