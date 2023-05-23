package com.triprint.backend.domain.user.dto;

import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public class MyProfileRequest {
	@NonNull
	private String username;
}
