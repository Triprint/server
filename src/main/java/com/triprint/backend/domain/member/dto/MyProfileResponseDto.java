package com.triprint.backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyProfileResponseDto {

	private String email;
	private String username;
	private String profile_img;
}
