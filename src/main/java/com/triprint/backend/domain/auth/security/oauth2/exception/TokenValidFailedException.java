package com.triprint.backend.domain.auth.security.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.triprint.backend.core.exception.ServiceException;

public class TokenValidFailedException extends ServiceException {

	public TokenValidFailedException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
