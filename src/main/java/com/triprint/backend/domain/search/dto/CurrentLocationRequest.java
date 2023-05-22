package com.triprint.backend.domain.search.dto;

import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentLocationRequest {

	@NonNull
	@Range(min = -180, max = 180)
	private String x;

	@NonNull
	@Range(min = -90, max = 90)
	private String y;

	@Range(min = 0, max = 100)
	private int distance = 20; //Todo: km로 받는 방식으로 변경
}
