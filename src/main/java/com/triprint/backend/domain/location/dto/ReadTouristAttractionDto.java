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
public class ReadTouristAttractionDto {
	@SuppressWarnings("checkstyle:MemberName")
	@NotNull
	private String x;

	@SuppressWarnings("checkstyle:MemberName")
	@NotNull
	private String y;

	@NotNull
	private String roadNameAddress;

	@NotNull
	private String name;
}
