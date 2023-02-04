package com.triprint.backend.domain.user.controller;

import com.triprint.backend.domain.user.dto.MyProfileImgResponse;
import com.triprint.backend.domain.user.dto.MyProfileRequest;
import com.triprint.backend.domain.user.dto.MyProfileResponse;
import com.triprint.backend.domain.user.service.MyProfileService;
import javax.servlet.http.HttpServletRequest;
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
	ResponseEntity<MyProfileResponse> getMyProfile(HttpServletRequest request) {
		return new ResponseEntity<>(myProfileService.getMyProfile((Long)request.getAttribute("userId")), HttpStatus.OK);
	}

	@PutMapping("/my/profile")
	ResponseEntity<MyProfileResponse> updateMyProfile(HttpServletRequest request, @RequestBody MyProfileRequest myProfileRequest) {
		return ResponseEntity.ok(myProfileService.updateMyProfile((Long)request.getAttribute("userId"), myProfileRequest.getUsername()));
	}

	@PutMapping("/my/profile-img")
	ResponseEntity<MyProfileImgResponse> updateMyProfileImg(
		HttpServletRequest request,
		@RequestPart(value = "file") MultipartFile multipartFile
	) {
		return ResponseEntity.ok(myProfileService.updateMyProfileImg((Long)request.getAttribute("userId"), multipartFile));
	}
}
