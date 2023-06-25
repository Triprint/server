package com.triprint.backend.domain.search.dto;

import com.triprint.backend.core.valid.ValidId;
import com.triprint.backend.core.valid.ValidIdNullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLocationRequest {
	@ValidId
	private Long cityId;

	@ValidIdNullable
	private Long districtId;
}
