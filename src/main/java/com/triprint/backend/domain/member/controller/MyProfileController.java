package com.triprint.backend.domain.member.controller;

import com.triprint.backend.domain.member.dto.MyProfileImgResponse;
import com.triprint.backend.domain.member.dto.MyProfileRequest;
import com.triprint.backend.domain.member.dto.MyProfileResponse;
import com.triprint.backend.domain.member.service.AwsS3Service;
import com.triprint.backend.domain.member.service.MyProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MyProfileController {

	private final MyProfileService myProfileService;
	private final AwsS3Service awsS3Service;

	@GetMapping("/my/profile")
	ResponseEntity<MyProfileResponse> getMyProfile(Long memberId) {
		return new ResponseEntity<>(myProfileService.getMyProfile(memberId), HttpStatus.OK);
	}

	@PutMapping("/my/profile")
	ResponseEntity<MyProfileResponse> updateMyProfile(Long memberId, @RequestBody MyProfileRequest myProfileRequest) {
		return ResponseEntity.ok(myProfileService.updateMyProfile(memberId, myProfileRequest.getUsername()));
	}

	@PutMapping("/my/profile-img")
	ResponseEntity<MyProfileImgResponse> updateMyProfileImg(
		Long memberId,
		@RequestPart(value = "file") MultipartFile multipartFile
	) {
		return ResponseEntity.ok(myProfileService.updateMyProfileImg(memberId, multipartFile));
	}

}
