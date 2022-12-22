package com.triprint.backend.core.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
	private final ErrorCode errorCode;

	protected ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
