package com.triprint.backend.domain.auth.security.oauth2.exception;

import com.triprint.backend.core.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class OAuthProviderMissMatchException extends ServiceException {
    public OAuthProviderMissMatchException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}