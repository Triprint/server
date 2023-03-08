package com.triprint.backend.domain.user.status;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	USER("ROLE_USER", "일반 사용자 권한"),
	ADMIN("ROLE_ADMIN", "관리자 권한"),
	GUEST("GUEST", "게스트 권한");

	private final String code;
	private final String displayName;

	public static UserRole of(String code) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(GUEST);
	}
}
