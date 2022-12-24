package com.triprint.backend.domain.member.service;

import com.triprint.backend.domain.member.dto.MyProfileResponseDto;
import com.triprint.backend.domain.member.entity.Member;
import com.triprint.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyProfileService {

	private final MemberRepository memberRepository;

	public MyProfileResponseDto getMyProfile(Long memberId) {

		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		return new MyProfileResponseDto(member.getEmail(), member.getUsername(), member.getProfileImg());
	}

	public void updateMyProfile(Long memberId, String username) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		member.editUsername(username);
	}


	public void createMyProfileImg(Long memberId, String profileImg) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		member.editProfileImg(profileImg);
	}

	public void updateMyProfileImg(Long memberId, String profileImg) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("email 에 해당하는 member 가 존재하지 않습니다.");
		});

		member.editProfileImg(profileImg);
	}

}
