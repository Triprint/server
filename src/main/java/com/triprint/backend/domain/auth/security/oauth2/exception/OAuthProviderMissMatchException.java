package com.triprint.backend.domain.auth.security.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.triprint.backend.core.exception.ServiceException;

public class OAuthProviderMissMatchException extends ServiceException {
	public OAuthProviderMissMatchException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
