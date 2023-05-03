package com.triprint.backend.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentLocationRequest {
	private String x;
	private String y;
}
