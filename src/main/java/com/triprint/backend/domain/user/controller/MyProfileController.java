package com.triprint.backend.domain.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.user.dto.GetMyTripResponse;
import com.triprint.backend.domain.user.dto.MyProfileImgResponse;
import com.triprint.backend.domain.user.dto.MyProfileRequest;
import com.triprint.backend.domain.user.dto.MyProfileResponse;
import com.triprint.backend.domain.user.service.MyProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyProfileController {

	private final MyProfileService myProfileService;

	@GetMapping("/trip")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<GetMyTripResponse>> getMyTripList(@CurrentUser UserPrincipal userPrincipal,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
		return ResponseEntity.ok(myProfileService.getMyTripList(userPrincipal.getId(), page));
	}

	@GetMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileResponse> getMyProfile(@CurrentUser UserPrincipal userPrincipal) {
		return ResponseEntity.ok(myProfileService.getMyProfile(userPrincipal.getId()));
	}

	@PutMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileResponse> updateMyProfile(HttpServletRequest request,
		@RequestBody MyProfileRequest myProfileRequest) {
		return ResponseEntity.ok(
			myProfileService.updateMyProfile((Long)request.getAttribute("userId"), myProfileRequest.getUsername()));
	}

	@PutMapping("/profile-img")
	@PreAuthorize("hasRole('ROLE_USER')")
	ResponseEntity<MyProfileImgResponse> updateMyProfileImg(
		HttpServletRequest request,
		@RequestPart(value = "file") MultipartFile multipartFile
	) {
		return ResponseEntity.ok(
			myProfileService.updateMyProfileImg((Long)request.getAttribute("userId"), multipartFile));
	}
}
