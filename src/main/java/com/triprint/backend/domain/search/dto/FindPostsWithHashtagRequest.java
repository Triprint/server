package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class FindPostsWithHashtagRequest {
	@Positive
	private Long id;
}
