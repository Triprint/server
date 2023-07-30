package com.triprint.backend.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.user.dto.MyProfileImgResponse;
import com.triprint.backend.domain.user.dto.MyProfileRequest;
import com.triprint.backend.domain.user.dto.MyProfileResponse;
import com.triprint.backend.domain.user.service.MyProfileService;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyProfileController {

	private final MyProfileService myProfileService;
	private final UserService userService;

	@GetMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileResponse> getMyProfile(@CurrentUser UserPrincipal userPrincipal) {
		return ResponseEntity.ok(myProfileService.getMyProfile(userPrincipal.getId()));
	}

	@PutMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileResponse> updateMyProfile(@CurrentUser UserPrincipal userPrincipal,
		@RequestBody MyProfileRequest myProfileRequest) {
		return ResponseEntity.ok(
			myProfileService.updateMyProfile(userPrincipal.getId(), myProfileRequest.getUsername()));
	}

	@PutMapping("/profile-img")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileImgResponse> updateMyProfileImg(
		@CurrentUser UserPrincipal userPrincipal,
		@RequestPart(value = "file") MultipartFile multipartFile
	) {
		return ResponseEntity.ok(
			myProfileService.updateMyProfileImg(userPrincipal.getId(), multipartFile));
	}

	@DeleteMapping("/quit")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<Object> quit(@CurrentUser UserPrincipal userPrincipal) throws Exception {
		userService.quit(userPrincipal.getId());
		return ResponseEntity.ok().build();
	}
}
