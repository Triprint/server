package com.triprint.backend.domain.location.dto;

import javax.validation.constraints.NotNull;

import com.triprint.backend.core.valid.ValidLocation;
import com.triprint.backend.core.valid.enums.LocationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTouristAttractionRequest {
	@ValidLocation(message = "올바른 x값을 입력해주세요", locationType = LocationType.LONGITUDE)
	private String x;

	@ValidLocation(message = "올바른 y값을 입력해주세요", locationType = LocationType.LATITUDE)
	private String y;

	@NotNull
	private String roadNameAddress;

	@NotNull
	private String name;
}
