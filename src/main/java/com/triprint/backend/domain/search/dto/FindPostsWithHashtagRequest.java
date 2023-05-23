package com.triprint.backend.domain.search.dto;

import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class FindPostsWithHashtagRequest {
	@NonNull
	@Positive
	private Long id;
}
