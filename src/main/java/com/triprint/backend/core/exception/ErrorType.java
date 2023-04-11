package com.triprint.backend.core.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
	USER_NOT_FOUND("일치하는 사용자가 존재하지 않습니다."),
	POST_NOT_FOUND("해당하는 게시물이 존재하지 않습니다."),
	TRIP_NOT_FOUND("해당하는 여행기가 존재하지 않습니다."),
	LIKE_NOT_FOUND("해당하는 좋아요가 존재하지 않습니다."),
	REPLY_NOT_FOUND("해당하는 댓글이 존재하지 않습니다."),
	IMAGE_NOT_FOUND("해당하는 이미지가 존재하지 않습니다."),
	HASHTAG_NOT_FOUND("해당하는 해시태그가 존재하지 않습니다."),
	TOURISTATTRACTION_NOT_FOUND("해당하는 관광지가 존재하지 않습니다."),
	DISTRICT_NOT_FOUND("해당하는 시*군*구가 존재하지 않습니다."),
	CITY_NOT_FOUND("해당하는 시*도가 존재하지 않습니다.");

	private final String errorMessage;

	ErrorType(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
