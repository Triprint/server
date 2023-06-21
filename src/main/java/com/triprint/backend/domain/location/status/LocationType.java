package com.triprint.backend.domain.location.status;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocationType {
	LONGITUDE("경도", "허용 범위: -180 ~ 180 "),
	LATITUDE("위도", "허용 범위: -90 ~ 90 ");

	private final String code;
	private final String allowableRange;

	public static Stream<LocationType> of(String code) {
		return Arrays.stream(LocationType.values())
			.filter(r -> r.getCode().equals(code));
	}
}
