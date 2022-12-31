package com.triprint.backend.domain.member.service;

import com.triprint.backend.domain.member.dto.MyProfileImgResponse;
import com.triprint.backend.domain.member.dto.MyProfileResponse;
import com.triprint.backend.domain.member.entity.Member;
import com.triprint.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MyProfileService {

	private final MemberRepository memberRepository;
	private final AwsS3Service awsS3Service;

	public MyProfileResponse getMyProfile(Long memberId) {

		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		return MyProfileResponse.builder()
			.email(member.getEmail())
			.username(member.getUsername())
			.profileImg(member.getProfileImg())
			.build();
	}

	public MyProfileResponse updateMyProfile(Long memberId, String username) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		try {
			updateUsername(member, username);
		} catch (Exception e) {
			throw new RuntimeException("중복되는 닉네임을 입력하셨습니다.");
		}

		return MyProfileResponse.builder()
			.email(member.getEmail())
			.username(member.getUsername())
			.profileImg(member.getProfileImg())
			.build();
	}

	@Transactional
	public MyProfileImgResponse updateMyProfileImg(Long memberId, MultipartFile multipartFile) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		String profileImg = "";

		try {
			profileImg = awsS3Service.uploadFile("profiles", multipartFile);
		} catch (Exception e) {
			throw new RuntimeException("이미지 파일이 정상적으로 업로드되지 않았습니다.");
		}

		member.editProfileImg(profileImg);

		return MyProfileImgResponse.builder().profileImg(profileImg).build();
	}

	@Transactional
	public void updateUsername(Member member, String username) {
		member.editUsername(username);
	}
}

