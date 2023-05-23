package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLocationRequest {
	@NonNull
	@Positive
	private Long cityId;

	@Nullable
	@Positive
	private Long districtId;
}
