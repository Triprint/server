package com.triprint.backend.domain.user.controller;

import com.triprint.backend.domain.user.dto.MyProfileImgResponse;
import com.triprint.backend.domain.user.dto.MyProfileRequest;
import com.triprint.backend.domain.user.dto.MyProfileResponse;
import com.triprint.backend.domain.user.service.MyProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MyProfileController {

	private final MyProfileService myProfileService;

	@GetMapping("/my/profile")
	ResponseEntity<MyProfileResponse> getMyProfile(Long userId) {
		return new ResponseEntity<>(myProfileService.getMyProfile(userId), HttpStatus.OK);
	}

	@PutMapping("/my/profile")
	ResponseEntity<MyProfileResponse> updateMyProfile(Long userId, @RequestBody MyProfileRequest myProfileRequest) {
		return ResponseEntity.ok(myProfileService.updateMyProfile(userId, myProfileRequest.getUsername()));
	}

	@PutMapping("/my/profile-img")
	ResponseEntity<MyProfileImgResponse> updateMyProfileImg(
		Long userId,
		@RequestPart(value = "file") MultipartFile multipartFile
	) {
		return ResponseEntity.ok(myProfileService.updateMyProfileImg(userId, multipartFile));
	}

}
