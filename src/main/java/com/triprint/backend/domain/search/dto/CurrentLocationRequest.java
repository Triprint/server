package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;

import com.triprint.backend.core.valid.ValidLocation;
import com.triprint.backend.core.valid.enums.LocationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentLocationRequest {
	// 얘는 경도 180
	@ValidLocation(message = "올바른 x값을 입력해주세요", locationType = LocationType.LONGITUDE)
	private String x;

	// 얘는 위도 90
	@ValidLocation(message = "올바른 y값을 입력해주세요", locationType = LocationType.LATITUDE)
	private String y;

	@Range(min = 0, max = 100)
	@PositiveOrZero
	private int distance = 20;
}
