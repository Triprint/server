package com.triprint.backend.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.trip.dto.GetMyTripResponse;
import com.triprint.backend.domain.trip.service.TripService;
import com.triprint.backend.domain.user.dto.MyProfileImgResponse;
import com.triprint.backend.domain.user.dto.MyProfileResponse;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyProfileService {

	private final UserRepository userRepository;
	private final AwsS3Service awsS3Service;
	private final TripService tripService;

	public MyProfileResponse getMyProfile(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Page<GetMyTripResponse> trips = tripService.getMyTripList(userId, Pageable.unpaged());

		return MyProfileResponse.builder()
			.id(userId)
			.email(user.getEmail())
			.username(user.getUsername())
			.profileImg(user.getProfileImg())
			.myTrips(trips)
			.build();
	}

	public MyProfileResponse updateMyProfile(Long userId, String username) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		try {
			updateUsername(user, username);
		} catch (Exception e) {
			throw new RuntimeException("중복되는 닉네임을 입력하셨습니다.");
		}

		return MyProfileResponse.builder()
			.id(userId)
			.email(user.getEmail())
			.username(user.getUsername())
			.profileImg(user.getProfileImg())
			.build();
	}

	@Transactional
	public MyProfileImgResponse updateMyProfileImg(Long userId, MultipartFile multipartFile) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		try {
			String profileImg = awsS3Service.uploadFile("profiles", multipartFile);
			user.editProfileImg(profileImg);
			return MyProfileImgResponse.builder().profileImg(profileImg).build();
		} catch (Exception e) {
			throw new RuntimeException("이미지 파일이 정상적으로 업로드되지 않았습니다.");
		}
	}

	@Transactional
	public void updateUsername(User user, String username) {
		user.editUsername(username);
		userRepository.save(user);
	}
}

