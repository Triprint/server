package com.triprint.backend.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ForbiddenException extends ServiceException {
	public ForbiddenException(String message) {
		super(HttpStatus.FORBIDDEN, message);
	}
}
