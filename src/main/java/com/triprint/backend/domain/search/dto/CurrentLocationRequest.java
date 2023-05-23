package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentLocationRequest {

	@NonNull
	@Digits(integer = 3, fraction = 6)
	@Range(min = -180, max = 180)
	private String x;

	@NonNull
	@Digits(integer = 2, fraction = 6)
	@Range(min = -90, max = 90)
	private String y;

	@Range(min = 0, max = 100)
	@PositiveOrZero
	private int distance = 20;
}
