package com.triprint.backend.domain.location.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLocationRequest {
	@NonNull
	private City city;

	@Nullable
	private District district;
}
