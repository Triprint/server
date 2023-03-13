package com.triprint.backend.domain.location.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTouristAttractionRequest {
	@NotNull
	private String x;

	@NotNull
	private String y;

	@NotNull
	private String roadNameAddress;

	@NotNull
	private String name;
}
