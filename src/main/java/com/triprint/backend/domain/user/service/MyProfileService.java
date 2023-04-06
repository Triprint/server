package com.triprint.backend.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.trip.repository.TripRepository;
import com.triprint.backend.domain.user.dto.GetMyTripResponse;
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
	private final TripRepository tripRepository;

	public MyProfileResponse getMyProfile(Long memberId) {

		User user = userRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		return MyProfileResponse.builder()
			.id(memberId)
			.email(user.getEmail())
			.username(user.getUsername())
			.profileImg(user.getProfileImg())
			.build();
	}

	public MyProfileResponse updateMyProfile(Long userId, String username) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 user 가 존재하지 않습니다.");
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
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
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

	public Page<GetMyTripResponse> getMyTripList(Long userId, Pageable page) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new ResourceNotFoundException("일치하는 user 가 없습니다."));
		Page<Trip> tripList = tripRepository.findByTripUser(user, page);

		return tripList.map((trip) -> new GetMyTripResponse(trip));
	}
}

