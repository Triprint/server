package com.triprint.backend.domain.search.dto;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentLocationRequest {
	@NonNull
	private String x;
	private String y;
	private int distance = 20000;
}
