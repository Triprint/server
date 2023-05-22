package com.triprint.backend.domain.search.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.triprint.backend.domain.location.dto.GetCityResponse;
import com.triprint.backend.domain.location.dto.GetDistrictResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLocationResponse {
	@NonNull
	private GetCityResponse city;

	@Nullable
	private GetDistrictResponse district;
}
