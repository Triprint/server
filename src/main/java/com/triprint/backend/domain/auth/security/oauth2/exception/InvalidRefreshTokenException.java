package com.triprint.backend.domain.auth.security.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.triprint.backend.core.exception.ServiceException;

import lombok.Getter;

@Getter
public class InvalidRefreshTokenException extends ServiceException {
	public InvalidRefreshTokenException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
