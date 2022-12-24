package com.triprint.backend.domain.member.controller;

import com.triprint.backend.domain.member.dto.MyProfileRequestDto;
import com.triprint.backend.domain.member.dto.MyProfileResponseDto;
import com.triprint.backend.domain.member.service.MyProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyProfileController {

	private MyProfileService myProfileService;

	@GetMapping("/my/profile")
	ResponseEntity<MyProfileResponseDto> getMyProfile(@RequestBody MyProfileRequestDto myProfileRequestDto) {
		return new ResponseEntity<>(myProfileService.getMyProfile(myProfileRequestDto.getEmail()), HttpStatus.OK);
	}

	@PutMapping("/my/profile")
	ResponseEntity<MyProfileResponseDto> updateMyProfile() {
		return null;
	}

	@PutMapping("/my/profile-img")
	ResponseEntity<MyProfileResponseDto> updateMyProfileImg() {
		return null;
	}

}
