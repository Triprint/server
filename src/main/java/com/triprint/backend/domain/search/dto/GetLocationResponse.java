package com.triprint.backend.domain.search.dto;

import com.triprint.backend.domain.location.dto.GetCityResponse;
import com.triprint.backend.domain.location.dto.GetDistrictResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLocationResponse {
	private GetCityResponse city;

	private GetDistrictResponse district;
}
