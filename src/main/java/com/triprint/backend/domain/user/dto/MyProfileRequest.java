package com.triprint.backend.domain.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class MyProfileRequest {
	@NotNull
	private String username;
}
